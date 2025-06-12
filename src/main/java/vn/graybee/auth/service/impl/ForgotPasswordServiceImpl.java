package vn.graybee.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.auth.model.ForgotPassword;
import vn.graybee.auth.repository.ForgotPasswordRepository;
import vn.graybee.auth.service.ForgotPasswordService;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@AllArgsConstructor
@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final MessageSourceUtil messageSourceUtil;

    @Override
    public ForgotPassword getByAccountIdAndOtp(Long accountId, Integer otp) {
        return forgotPasswordRepository.findByAccountIdAndOtp(accountId, otp)
                .orElseThrow(() -> new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("auth.otp.invalid")));
    }

    @Override
    public void deleteById(Integer id) {
        forgotPasswordRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveByAccountIdAndDeleteAll(Long accountId, Integer otp) {

        Instant nowInstant = Instant.now();
        Instant expirationInstant = nowInstant.plus(5, ChronoUnit.MINUTES);


        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.setOtp(otp);
        forgotPassword.setAccountId(accountId);
        forgotPassword.setExpiration(Date.from(expirationInstant));
        forgotPassword.setVerify(false);

        forgotPasswordRepository.deleteAllByAccountId(accountId);

        forgotPasswordRepository.save(forgotPassword);
    }

    @Override
    public ForgotPassword findByAccountId(Long accountId) {
        return forgotPasswordRepository.findFirstByAccountId(accountId).orElseThrow(() -> new BusinessCustomException(Constants.Common.root, messageSourceUtil.get("auth.not.verify.email")));
    }

    @Override
    public void verifyOtpById(Integer id) {
        forgotPasswordRepository.verifyOtpById(id);
    }

}
