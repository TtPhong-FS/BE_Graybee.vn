package vn.graybee.auth.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.account.model.Account;
import vn.graybee.account.model.Profile;
import vn.graybee.account.repository.AccountRepository;
import vn.graybee.account.repository.ProfileRepository;
import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.auth.dto.request.LoginRequest;
import vn.graybee.auth.dto.response.AccountAuthDto;
import vn.graybee.auth.dto.response.AuthDto;
import vn.graybee.auth.dto.response.RegisterDto;
import vn.graybee.auth.enums.Role;
import vn.graybee.auth.exception.AuthException;
import vn.graybee.auth.repository.ForgotPasswordRepository;
import vn.graybee.auth.service.AuthService;
import vn.graybee.auth.service.JwtService;
import vn.graybee.auth.service.RedisAuthService;
import vn.graybee.cart.model.Cart;
import vn.graybee.cart.repository.CartRepository;
import vn.graybee.common.constants.ConstantAuth;
import vn.graybee.common.constants.ConstantProfile;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.service.MailService;
import vn.graybee.common.utils.CodeGenerator;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.order.repository.OrderRepository;
import vn.graybee.record.ResetPassword;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    private final OrderRepository orderRepository;

    private final AuthenticationManager authenticationManager;

    private final ProfileRepository profileRepository;

    private final MessageSourceUtil messageSourceUtil;

    private final JwtService jwtService;

    private final MailService mailService;

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final RedisAuthService redisAuthService;

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final CartRepository cartRepository;

    public AuthServiceImpl(OrderRepository orderRepository, AuthenticationManager authenticationManager, ProfileRepository profileRepository, MessageSourceUtil messageSourceUtil, JwtService jwtService, MailService mailService, ForgotPasswordRepository forgotPasswordRepository, RedisAuthService redisAuthService, AccountRepository accountRepository, BCryptPasswordEncoder passwordEncoder, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.authenticationManager = authenticationManager;
        this.profileRepository = profileRepository;
        this.messageSourceUtil = messageSourceUtil;
        this.jwtService = jwtService;
        this.mailService = mailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.redisAuthService = redisAuthService;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<RegisterDto> signUp(CustomerRegisterRequest request, String sessionId) {

        if (profileRepository.checkExistsEmail(request.getEmail())) {
            throw new BusinessCustomException(ConstantProfile.email.toLowerCase(), messageSourceUtil.get("profile.error.exists", new Object[]{ConstantProfile.email}));
        }

        if (profileRepository.checkExistsPhone(request.getPhone())) {
            throw new BusinessCustomException(ConstantProfile.phone.toLowerCase(), messageSourceUtil.get("profile.error.exists", new Object[]{ConstantProfile.phone}));
        }

        String uid = CodeGenerator.generateCode(10, CodeGenerator.DIGITS);

        Account account = new Account();

        account.setUid(uid);
        account.setRole(Role.CUSTOMER);
        account.setUsername(request.getPhone());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setActive(true);
        account.setSuperAdmin(false);

        account = accountRepository.save(account);

        Profile profile = new Profile();
        profile.setAccountId(account.getId());
        profile.setAvatarUrl(null);
        profile.setEmail(request.getEmail());
        profile.setPhone(request.getPhone());
        profile.setBirthDay(request.getBirthDay());
        profile.setFullName(request.getFullName());
        profile.setGender(request.getGender());

        profileRepository.save(profile);

        Cart cart = cartRepository.findBySessionId(sessionId).orElseGet(Cart::new);

        cart.setAccountId(account.getId());
        cart.setTotalAmount(BigDecimal.ZERO);

        cartRepository.save(cart);

        if (sessionId != null && !sessionId.isEmpty()) {
            List<Long> orderIds = orderRepository.findIdsBySessionId(sessionId);

            orderRepository.transformOrdersToAccountByIds(orderIds, account.getId());
        }

        String token = jwtService.generateToken(account.getUid(), account.getRole());
        redisAuthService.saveToken(account.getUid(), token, 1440, TimeUnit.MINUTES);

        AuthDto authDto = new AuthDto();
        authDto.setToken(token);

        RegisterDto registerDto = new RegisterDto(profile, authDto);

        return new BasicMessageResponse<>(201, messageSourceUtil.get("auth.success.signup"), registerDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<AuthDto> Login(LoginRequest request) {

        AccountAuthDto auth = accountRepository.findByUsername(request.getPhone())
                .orElseThrow(() -> new AuthException(ConstantAuth.AUTH_USER_NOT_FOUND, messageSourceUtil.get("auth.user.invalid_credentials")));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auth.getUid(), request.getPassword()
                )
        );

        String token = jwtService.generateToken(auth.getUid(), auth.getRole());

        AuthDto authDto = new AuthDto();
        authDto.setToken(token);

//        customerRepository.updateStatus(AccountStatus.ONLINE, user.getUid());

        redisAuthService.saveToken(auth.getUid(), token, 1440, TimeUnit.MINUTES);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("auth.success.login"), authDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<String> verifyEmail(String email) {

//        Integer userId = customerRepository.getIdByEmail(email).orElseThrow(() -> new BusinessCustomException(ConstantUser.email, messageSourceUtil.get("auth.mail.invalid")));
//        Integer otp = Integer.valueOf(CodeGenerator.generateCode(6, CodeGenerator.DIGITS));
//
//        MailBody mailBody = new MailBody(
//                email,
//                messageSourceUtil.get("auth.mail.subject"),
//                messageSourceUtil.get("auth.mail.text", new Object[]{otp})
//        );
//
//        long now = System.currentTimeMillis();
//
//        ForgotPassword forgotPassword = new ForgotPassword();
//        forgotPassword.setOtp(otp);
//        forgotPassword.setUserId(userId);
//        forgotPassword.setExpiration(new Date(now + 2 * 60 * 1000));
//
//        forgotPasswordRepository.deleteAllInBatch();
//
//        forgotPasswordRepository.save(forgotPassword);
//        mailService.sendMail(mailBody);
//
//
//        return new BasicMessageResponse<>(200, messageSourceUtil.get("auth.send.mail", new Object[]{email}), null);

        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<String> verifyOtp(Integer otp, String email) {
//
//        Integer userId = userRepository.getIdByEmail(email).orElseThrow(() -> new BusinessCustomException(ConstantUser.email, messageSourceUtil.get("auth.mail.invalid")));
//
//        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUserId(otp, userId)
//                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.root, messageSourceUtil.get("auth.otp.invalid")));
//
//        if (forgotPassword.getExpiration().before(Date.from(Instant.now()))) {
//            forgotPasswordRepository.deleteById(forgotPassword.getId());
//
//            return new BasicMessageResponse<>(400, messageSourceUtil.get("auth.otp.invalid"), null);
//        }
//
//        return new BasicMessageResponse<>(200, messageSourceUtil.get("auth.verify.otp"), null);

        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<String> resetPassword(String email, ResetPassword resetPassword) {
//
//        userRepository.validateEmail(email).orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("auth.mail.invalid")));
//
//        if (!Objects.equals(resetPassword.password(), resetPassword.repeatPassword())) {
//            throw new BusinessCustomException(ConstantGeneral.root, messageSourceUtil.get("auth.password.not_match"));
//        }
//
//        String password = passwordEncoder.encode(resetPassword.password());
//
//        userRepository.updatePassword(email, password);
//
//        return new BasicMessageResponse<>(200, messageSourceUtil.get("auth.success.reset_password"), null);

        return null;
    }

}
