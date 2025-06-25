package vn.graybee.auth.service;

import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.auth.dto.request.LoginRequest;
import vn.graybee.auth.dto.response.LoginResponse;
import vn.graybee.auth.dto.response.RegisterResponse;
import vn.graybee.auth.record.ResetPassword;
import vn.graybee.common.dto.BasicMessageResponse;

public interface AuthService {

    RegisterResponse signUp(CustomerRegisterRequest request, String sessionId);

    LoginResponse Login(LoginRequest request);

    LoginResponse LoginDashboard(LoginRequest request);

    BasicMessageResponse<String> verifyEmail(String email);

    BasicMessageResponse<String> verifyOtp(Integer otp, String email);

    void resetPassword(String email, ResetPassword resetPassword);

}
