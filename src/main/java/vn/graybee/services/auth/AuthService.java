package vn.graybee.services.auth;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.record.ResetPassword;
import vn.graybee.requests.auth.LoginRequest;
import vn.graybee.requests.auth.SignUpRequest;
import vn.graybee.response.publics.auth.AuthResponse;

public interface AuthService {

    BasicMessageResponse<AuthResponse> signUp(SignUpRequest request, String sessionId);

    BasicMessageResponse<AuthResponse> Login(LoginRequest request);

    BasicMessageResponse<String> verifyEmail(String email);

    BasicMessageResponse<String> verifyOtp(Integer otp, String email);

    BasicMessageResponse<String> resetPassword(String email, ResetPassword resetPassword);

}
