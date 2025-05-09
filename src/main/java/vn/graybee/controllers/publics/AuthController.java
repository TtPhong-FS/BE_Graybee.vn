package vn.graybee.controllers.publics;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.auth.LoginRequest;
import vn.graybee.requests.auth.SignUpRequest;
import vn.graybee.response.publics.auth.AuthResponse;
import vn.graybee.services.auth.AuthService;

@RestController
@RequestMapping("${api.auth}")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<BasicMessageResponse<AuthResponse>> SignUp(@RequestBody @Valid SignUpRequest request, @CookieValue(value = "sessionId", required = false) String sessionId) {
        return ResponseEntity.ok(authService.signUp(request, sessionId));
    }

    @PostMapping("/login")
    public ResponseEntity<BasicMessageResponse<AuthResponse>> Login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.Login(request));
    }


}
