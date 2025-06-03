package vn.graybee.auth.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.auth.dto.request.LoginRequest;
import vn.graybee.auth.dto.response.AuthDto;
import vn.graybee.auth.dto.response.RegisterDto;
import vn.graybee.auth.record.ResetPassword;
import vn.graybee.auth.service.AuthService;
import vn.graybee.common.dto.BasicMessageResponse;

@RestController
@RequestMapping("${api.publicApi.auth}")
public class CustomerAuthController {

    private final AuthService authService;

    public CustomerAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<BasicMessageResponse<RegisterDto>> SignUp(@RequestBody @Valid CustomerRegisterRequest request, @CookieValue(value = "sessionId", required = false) String sessionId) {
        return ResponseEntity.ok(authService.signUp(request, sessionId));
    }

    @PostMapping("/login")
    public ResponseEntity<BasicMessageResponse<AuthDto>> Login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.Login(request));
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
    public ResponseEntity<BasicMessageResponse<String>> resetPassword(@RequestBody ResetPassword resetPassword, @PathVariable("email") String email) {
        return ResponseEntity.ok(authService.resetPassword(email, resetPassword));
    }

}
