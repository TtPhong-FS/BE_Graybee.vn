package vn.graybee.auth.service;

import vn.graybee.auth.model.ForgotPassword;

public interface ForgotPasswordService {

    ForgotPassword getByAccountIdAndOtp(Long accountId, Integer otp);

    void deleteById(Integer id);

    void saveByAccountIdAndDeleteAll(Long accountId, Integer otp);

    ForgotPassword findByAccountId(Long accountId);

    void verifyOtpById(Integer id);

}
