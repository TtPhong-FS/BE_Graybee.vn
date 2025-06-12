package vn.graybee.auth.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.auth.dto.request.LoginRequest;
import vn.graybee.auth.dto.response.LoginResponse;
import vn.graybee.auth.dto.response.RegisterResponse;
import vn.graybee.auth.record.ResetPassword;
import vn.graybee.auth.service.AuthService;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.common.utils.MessageSourceUtil;

@AllArgsConstructor
@RestController
@RequestMapping("${api.publicApi.auth}")
public class AuthController {

    private final AuthService authService;

    private final MessageSourceUtil messageSourceUtil;

    @PostMapping("/signup")
    public ResponseEntity<BasicMessageResponse<RegisterResponse>> SignUp(@RequestBody @Valid CustomerRegisterRequest request, @CookieValue(value = "sessionId", required = false) String sessionId) {
        return ResponseEntity.ok(
                MessageBuilder.ok(authService.signUp(request, sessionId), messageSourceUtil.get("auth.success.signup"))
        );
    }

    @PostMapping("/login")
    public ResponseEntity<BasicMessageResponse<LoginResponse>> Login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        authService.Login(request),
                        messageSourceUtil.get("auth.success.login")
                )
        );
    }

    @PostMapping("/forgot-password/verify-email/{email}")
    public ResponseEntity<BasicMessageResponse<String>> verifyEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(authService.verifyEmail(email));
    }

    @PostMapping("/forgot-password/verify-otp/{otp}/{email}")
    public ResponseEntity<BasicMessageResponse<String>> verifyOtp(@PathVariable("otp") Integer otp, @PathVariable("email") String email) {
        return ResponseEntity.ok(authService.verifyOtp(otp, email));
    }

    @PostMapping("/forgot-password/reset-password/{email}")
    public ResponseEntity<BasicMessageResponse<?>> resetPassword(@Valid @RequestBody ResetPassword resetPassword, @PathVariable("email") String email) {
        authService.resetPassword(email, resetPassword);
        return ResponseEntity.ok(
                MessageBuilder.ok(null, messageSourceUtil.get("auth.success.reset_password"))
        );
    }

}
