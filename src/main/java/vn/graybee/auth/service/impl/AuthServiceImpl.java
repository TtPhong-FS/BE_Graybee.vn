package vn.graybee.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.auth.dto.request.LoginRequest;
import vn.graybee.auth.dto.response.AccountAuthDto;
import vn.graybee.auth.dto.response.AuthDto;
import vn.graybee.auth.dto.response.LoginResponse;
import vn.graybee.auth.dto.response.RegisterResponse;
import vn.graybee.auth.enums.Role;
import vn.graybee.auth.exception.AuthException;
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
import vn.graybee.modules.account.dto.response.ProfileResponse;
import vn.graybee.modules.account.model.Account;
import vn.graybee.modules.account.service.AccountService;
import vn.graybee.modules.account.service.CustomerService;
import vn.graybee.modules.account.service.ProfileService;
import vn.graybee.modules.cart.service.CartService;
import vn.graybee.modules.order.service.OrderService;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final OrderService orderService;

    private final ProfileService profileService;

    private final AuthenticationManager authenticationManager;

    private final MessageSourceUtil messageSourceUtil;

    private final JwtService jwtService;

    private final CustomerService customerService;

    private final MailService mailService;

    private final ForgotPasswordService forgotPasswordService;

    private final RedisAuthService redisAuthService;

    private final AccountService accountService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final CartService cartService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RegisterResponse signUp(CustomerRegisterRequest request, String sessionId) {

        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new BusinessCustomException(Constants.Auth.password, messageSourceUtil.get("auth.password.not_match"));
        }

        Account account = accountService.saveAccount(request);

        ProfileResponse profile = profileService.saveProfileByAccountId(account.getId(), request.getProfile());

        customerService.saveCustomerByAccount(account.getId());

        cartService.syncGuestCartToAccount(account.getId(), sessionId);

        orderService.transformOrdersToAccountBySessionId(account.getId(), sessionId);
        String token = jwtService.generateToken(account.getUid(), account.getRole());
        redisAuthService.saveToken(account.getUid(), token, 1440, TimeUnit.MINUTES);

        AuthDto authDto = new AuthDto(token);

        return new RegisterResponse(profile, authDto);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public LoginResponse Login(LoginRequest request) {

        AccountAuthDto auth = accountService.getAccountAuthDtoByEmail(request.getEmail());

        if (!auth.isActive()) {
            throw new AuthException(Constants.Common.root, messageSourceUtil.get("auth.account_locked"));
        }

        if (!passwordEncoder.matches(request.getPassword(), auth.getPassword())) {
            throw new BusinessCustomException(Constants.Common.root, messageSourceUtil.get("auth.invalid_credentials"));
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auth.getUid(), request.getPassword()
                )
        );

        String token = jwtService.generateToken(auth.getUid(), auth.getRole());

        ProfileResponse profileResponse = profileService.findByAccountId(auth.getId());

        AuthDto authDto = new AuthDto(token);

        redisAuthService.saveToken(auth.getUid(), token, 1440, TimeUnit.MINUTES);

        accountService.updateLastLoginAt(auth.getId());

        return new LoginResponse(
                authDto,
                profileResponse
        );
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public LoginResponse LoginDashboard(LoginRequest request) {

        AccountAuthDto auth = accountService.getAccountAuthDtoByEmail(request.getEmail());

        if (auth.getRole() == Role.CUSTOMER) {
            throw new BusinessCustomException(Constants.Common.root, "Bạn không có quyền để đăng nhập vào hệ thống quản trị");
        }

        if (!auth.isActive()) {
            throw new AuthException(Constants.Common.root, messageSourceUtil.get("auth.account_locked"));
        }

        if (!passwordEncoder.matches(request.getPassword(), auth.getPassword())) {
            throw new BusinessCustomException(Constants.Common.root, messageSourceUtil.get("auth.invalid_credentials"));
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auth.getUid(), request.getPassword()
                )
        );

        String token = jwtService.generateToken(auth.getUid(), auth.getRole());

        ProfileResponse profileResponse = profileService.findByAccountId(auth.getId());

        AuthDto authDto = new AuthDto(token);

        redisAuthService.saveToken(auth.getUid(), token, 1440, TimeUnit.MINUTES);

        accountService.updateLastLoginAt(auth.getId());

        return new LoginResponse(
                authDto,
                profileResponse
        );
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<String> verifyEmail(String email) {

        Long accountId = accountService.getAccountIdByEmail(email);

        Integer otp = CodeGenerator.generateOtp();

        MailBody mailBody = new MailBody(
                email,
                messageSourceUtil.get("auth.mail.subject"),
                messageSourceUtil.get("auth.mail.text", new Object[]{String.valueOf(otp)})
        );

        forgotPasswordService.saveByAccountIdAndDeleteAll(accountId, otp);

        mailService.sendMail(mailBody);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("auth.send.mail", new Object[]{email}), null);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<String> verifyOtp(Integer otp, String email) {

        Long accountId = accountService.getAccountIdByEmail(email);

        ForgotPassword forgotPassword = forgotPasswordService.getByAccountIdAndOtp(accountId, otp);

        if (forgotPassword.isVerify()) {
            forgotPasswordService.deleteById(forgotPassword.getId());

            return new BasicMessageResponse<>(400, messageSourceUtil.get("auth.otp.invalid"), null);
        }

        if (forgotPassword.getExpiration().before(Date.from(Instant.now()))) {
            forgotPasswordService.deleteById(forgotPassword.getId());

            return new BasicMessageResponse<>(400, messageSourceUtil.get("auth.otp.invalid"), null);
        }

        forgotPasswordService.verifyOtpById(forgotPassword.getId());

        return new BasicMessageResponse<>(200, messageSourceUtil.get("auth.verify.otp"), null);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void resetPassword(String email, ResetPassword resetPassword) {

        Long accountId = accountService.getAccountIdByEmail(email);

        ForgotPassword forgotPassword = forgotPasswordService.findByAccountId(accountId);

        if (!forgotPassword.isVerify()) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("auth.not.verify.otp"));
        }

        if (!resetPassword.getPassword().equals(resetPassword.getRepeatPassword())) {
            throw new BusinessCustomException(Constants.Common.root, messageSourceUtil.get("auth.password.not_match"));
        }

        String password = passwordEncoder.encode(resetPassword.getPassword());

        accountService.updatePasswordById(accountId, password);

    }

}
