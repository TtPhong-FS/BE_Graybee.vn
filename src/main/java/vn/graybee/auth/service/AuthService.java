package vn.graybee.auth.service;

import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.auth.dto.request.LoginRequest;
import vn.graybee.auth.dto.response.AuthDto;
import vn.graybee.auth.dto.response.RegisterDto;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.record.ResetPassword;

public interface AuthService {

    BasicMessageResponse<RegisterDto> signUp(CustomerRegisterRequest request, String sessionId);

    BasicMessageResponse<AuthDto> Login(LoginRequest request);

    BasicMessageResponse<String> verifyEmail(String email);

    BasicMessageResponse<String> verifyOtp(Integer otp, String email);

    BasicMessageResponse<String> resetPassword(String email, ResetPassword resetPassword);

}
