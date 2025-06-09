package vn.graybee.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.auth.dto.request.LoginRequest;
import vn.graybee.auth.dto.response.AccountAuthDto;
import vn.graybee.auth.dto.response.AuthDto;
import vn.graybee.auth.dto.response.RegisterDto;
import vn.graybee.auth.model.ForgotPassword;
import vn.graybee.auth.record.MailBody;
import vn.graybee.auth.record.ResetPassword;
import vn.graybee.auth.service.AuthService;
import vn.graybee.auth.service.ForgotPasswordService;
import vn.graybee.auth.service.JwtService;
import vn.graybee.auth.service.RedisAuthService;
import vn.graybee.common.Constants;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.service.MailService;
import vn.graybee.common.utils.CodeGenerator;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.model.Account;
import vn.graybee.modules.account.model.Profile;
import vn.graybee.modules.account.service.AccountService;
import vn.graybee.modules.account.service.ProfileService;
import vn.graybee.modules.cart.service.CartService;
import vn.graybee.modules.order.service.OrderService;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private static final long now = System.currentTimeMillis();

    private final OrderService orderService;

    private final ProfileService profileService;

    private final AuthenticationManager authenticationManager;

    private final MessageSourceUtil messageSourceUtil;

    private final JwtService jwtService;

    private final MailService mailService;

    private final ForgotPasswordService forgotPasswordService;

    private final RedisAuthService redisAuthService;

    private final AccountService accountService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final CartService cartService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<RegisterDto> signUp(CustomerRegisterRequest request, String sessionId) {

        accountService.checkExistsByEmail(request.getEmail());
        profileService.checkExistsByPhone(request.getPhone());

        Account account = accountService.saveAccount(request);

        Profile profile = profileService.saveProfileByAccountId(account.getId(), request);

        cartService.syncGuestCartToAccount(account.getId(), sessionId);

        orderService.transformOrdersToAccountBySessionId(account.getId(), sessionId);

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

        AccountAuthDto auth = accountService.getAccountAuthDtoByEmail(request.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auth.getUid(), request.getPassword()
                )
        );

        String token = jwtService.generateToken(auth.getUid(), auth.getRole());

        AuthDto authDto = new AuthDto();
        authDto.setToken(token);

        redisAuthService.saveToken(auth.getUid(), token, 1440, TimeUnit.MINUTES);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("auth.success.login"), authDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<String> verifyEmail(String email) {

        Long accountId = accountService.getAccountIdByEmail(email);

        Integer otp = Integer.valueOf(CodeGenerator.generateCode(6, CodeGenerator.DIGITS));

        MailBody mailBody = new MailBody(
                email,
                messageSourceUtil.get("auth.mail.subject"),
                messageSourceUtil.get("auth.mail.text", new Object[]{otp})
        );

        forgotPasswordService.saveByAccountIdAndDeleteAll(accountId, otp, now);

        mailService.sendMail(mailBody);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("auth.send.mail", new Object[]{email}), null);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<String> verifyOtp(Integer otp, String email) {

        Long accountId = accountService.getAccountIdByEmail(email);

        ForgotPassword forgotPassword = forgotPasswordService.getByAccountIdAndOtp(accountId, otp);

        if (forgotPassword.getExpiration().before(Date.from(Instant.now()))) {
            forgotPasswordService.deleteById(forgotPassword.getId());

            return new BasicMessageResponse<>(400, messageSourceUtil.get("auth.otp.invalid"), null);
        }

        return new BasicMessageResponse<>(200, messageSourceUtil.get("auth.verify.otp"), null);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<String> resetPassword(String email, ResetPassword resetPassword) {

        Long accountId = accountService.getAccountIdByEmail(email);

        if (!Objects.equals(resetPassword.password(), resetPassword.repeatPassword())) {
            throw new BusinessCustomException(Constants.Common.root, messageSourceUtil.get("auth.password.not_match"));
        }

        String password = passwordEncoder.encode(resetPassword.password());

        accountService.updatePasswordById(accountId, password);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("auth.success.reset_password"), null);

    }

}
