package vn.graybee.services.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantAuth;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantUser;
import vn.graybee.enums.AccountStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.orders.Cart;
import vn.graybee.models.users.ForgotPassword;
import vn.graybee.models.users.User;
import vn.graybee.models.users.UserPrincipalDto;
import vn.graybee.record.MailBody;
import vn.graybee.record.ResetPassword;
import vn.graybee.repositories.auths.RoleRepository;
import vn.graybee.repositories.orders.CartRepository;
import vn.graybee.repositories.orders.OrderRepository;
import vn.graybee.repositories.users.ForgotPasswordRepository;
import vn.graybee.repositories.users.UserRepository;
import vn.graybee.requests.auth.LoginRequest;
import vn.graybee.requests.auth.SignUpRequest;
import vn.graybee.response.publics.auth.AuthResponse;
import vn.graybee.response.users.UserAuthenDto;
import vn.graybee.services.MailServices;
import vn.graybee.utils.CodeGenerator;
import vn.graybee.utils.TextUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    private final OrderRepository orderRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtServices jwtServices;

    private final MailServices mailServices;

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final RedisAuthServices redisAuthServices;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final CartRepository cartRepository;

    public AuthServiceImpl(OrderRepository orderRepository, AuthenticationManager authenticationManager, JwtServices jwtServices, MailServices mailServices, ForgotPasswordRepository forgotPasswordRepository, RedisAuthServices redisAuthServices, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.authenticationManager = authenticationManager;
        this.jwtServices = jwtServices;
        this.mailServices = mailServices;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.redisAuthServices = redisAuthServices;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<AuthResponse> signUp(SignUpRequest request, String sessionId) {

        if (userRepository.validateEmail(request.getEmail()).isPresent()) {
            throw new BusinessCustomException(ConstantUser.email, ConstantUser.email_exists);
        }

        if (userRepository.validatePhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new BusinessCustomException(ConstantUser.phoneNumber, ConstantUser.phoneNumber_exists);
        }

        int roleId = roleRepository.getIdByRoleCustomer()
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantAuth.role_does_not_exists));

        Integer uid = Integer.valueOf(CodeGenerator.generateCode(6, CodeGenerator.DIGITS));

        User user = new User();

        user.setActive(true);
        user.setSuperAdmin(false);
        user.setRoleId(roleId);
        user.setFullName(TextUtils.capitalizeEachWord(request.getFullName()));
        user.setUid(uid);
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setStatus(AccountStatus.ACTIVE);

        user = userRepository.save(user);

        Cart cart = cartRepository.findBySessionId(sessionId).orElseGet(Cart::new);

        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setSessionId(null);
        cart.setUserUid(user.getUid());
        cartRepository.save(cart);

        if (sessionId != null && !sessionId.isEmpty()) {
            List<Long> orderIds = orderRepository.findIdsBySessionId(sessionId);

            orderRepository.transformOrdersToUserUidByIds(orderIds, user.getUid());
        }

        UserPrincipalDto userPrincipalDto = new UserPrincipalDto();
        userPrincipalDto.setUsername(user.getUsername());
        userPrincipalDto.setRoleName("CUSTOMER");

        String token = jwtServices.generateToken(user.getUsername(), userPrincipalDto.getRoleName());
        redisAuthServices.saveToken(user.getUid(), token, 1440, TimeUnit.MINUTES);

        AuthResponse response = new AuthResponse();
        response.setToken(token);

        return new BasicMessageResponse<>(201, ConstantAuth.success_signup, response);
    }

    @Override
    public BasicMessageResponse<AuthResponse> Login(LoginRequest request) {

        UserAuthenDto user = userRepository.getAuthenBasicByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessCustomException(ConstantAuth.password, ConstantAuth.wrong_password);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        String token = jwtServices.generateToken(request.getUsername(), user.getRole());

        AuthResponse response = new AuthResponse();
        response.setToken(token);

        userRepository.updateStatus(AccountStatus.ONLINE, user.getUid());

        redisAuthServices.saveToken(user.getUid(), token, 1440, TimeUnit.MINUTES);

        return new BasicMessageResponse<>(200, ConstantAuth.success_login, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<String> verifyEmail(String email) {


        Integer userId = userRepository.getIdByEmail(email).orElseThrow(() -> new BusinessCustomException(ConstantUser.email, ConstantUser.email_invalid));
        Integer otp = Integer.valueOf(CodeGenerator.generateCode(6, CodeGenerator.DIGITS));

        MailBody mailBody = new MailBody(
                email,
                "OTP for Forgot Password request",
                "This is the OTP for your forgot password request: " + otp
        );

        long now = System.currentTimeMillis();

        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.setOtp(otp);
        forgotPassword.setUserId(userId);
        forgotPassword.setExpiration(new Date(now + 2 * 60 * 1000));

        forgotPasswordRepository.deleteAllInBatch();

        forgotPasswordRepository.save(forgotPassword);
        mailServices.sendMail(mailBody);


        return new BasicMessageResponse<>(200, ConstantAuth.success_verify_email + email, null);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<String> verifyOtp(Integer otp, String email) {

        Integer userId = userRepository.getIdByEmail(email).orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantUser.email_invalid));

        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUserId(otp, userId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.root, ConstantAuth.invalid_otp));

        if (forgotPassword.getExpiration().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(forgotPassword.getId());

            return new BasicMessageResponse<>(400, ConstantAuth.otp_expired, null);
        }

        return new BasicMessageResponse<>(200, ConstantAuth.success_verify_otp, null);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<String> resetPassword(String email, ResetPassword resetPassword) {

        userRepository.validateEmail(email).orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantUser.email_invalid));

        if (!Objects.equals(resetPassword.password(), resetPassword.repeatPassword())) {
            throw new BusinessCustomException(ConstantGeneral.root, ConstantAuth.password_not_matches_repeatPassword);
        }

        String password = passwordEncoder.encode(resetPassword.password());

        userRepository.updatePassword(email, password);

        return new BasicMessageResponse<>(200, ConstantAuth.success_reset_password, null);
    }

}
