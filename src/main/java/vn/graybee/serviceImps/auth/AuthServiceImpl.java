package vn.graybee.serviceImps.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantAuth;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantUser;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.orders.Cart;
import vn.graybee.models.users.User;
import vn.graybee.models.users.UserPrincipalDto;
import vn.graybee.repositories.auths.RoleRepository;
import vn.graybee.repositories.orders.CartRepository;
import vn.graybee.repositories.users.UserRepository;
import vn.graybee.requests.auth.LoginRequest;
import vn.graybee.requests.auth.SignUpRequest;
import vn.graybee.response.publics.auth.AuthResponse;
import vn.graybee.services.auth.AuthService;
import vn.graybee.utils.TextUtils;
import vn.graybee.utils.UidGenerator;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtServices jwtServices;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final CartRepository cartRepository;


    public AuthServiceImpl(JwtServices jwtServices, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, CartRepository cartRepository) {
        this.jwtServices = jwtServices;
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

        int uid = UidGenerator.generateUid();

        User user = new User();

        user.setActive(false);
        user.setRoleId(roleId);
        user.setFullName(TextUtils.capitalizeEachWord(request.getFullName()));
        user.setUid(uid);
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUsername(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setStatus("PENDING");

        user = userRepository.save(user);

        Cart cart = cartRepository.findBySessionId(sessionId).orElseGet(Cart::new);

        cart.setSessionId(null);
        cart.setUserUid(user.getUid());

        cartRepository.save(cart);

        UserPrincipalDto userPrincipalDto = new UserPrincipalDto();
        userPrincipalDto.setUsername(user.getUsername());
        userPrincipalDto.setROLE_NAME("CUSTOMER");

        String token = jwtServices.generateToken(userPrincipalDto);

        AuthResponse response = new AuthResponse();
        response.setToken(token);

        return new BasicMessageResponse<>(201, ConstantAuth.success_signup, response);
    }

    @Override
    public BasicMessageResponse<AuthResponse> Login(LoginRequest request) {

        String password = userRepository.getPasswordByUsername(request.getUsername());

        if (userRepository.checkExistsByUsername(request.getUsername()).isEmpty()) {
            throw new BusinessCustomException(ConstantAuth.username, ConstantAuth.wrong_username);
        }

        if (!passwordEncoder.matches(request.getPassword(), password)) {
            throw new BusinessCustomException(ConstantAuth.password, ConstantAuth.wrong_password);
        }

        UserPrincipalDto user = userRepository.findByUserName(request.getUsername())
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantUser.does_not_exists));

        String token = jwtServices.generateToken(user);

        AuthResponse response = new AuthResponse();
        response.setToken(token);

        return new BasicMessageResponse<>(200, ConstantAuth.success_login, response);
    }

}
