package vn.graybee.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.graybee.auth.model.ForgotPassword;
import vn.graybee.auth.repository.ForgotPasswordRepository;
import vn.graybee.auth.service.ForgotPasswordService;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

import java.util.Date;

@AllArgsConstructor
@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final MessageSourceUtil messageSourceUtil;

    @Override
    public ForgotPassword getByAccountIdAndOtp(Long accountId, Integer otp) {
        return forgotPasswordRepository.findByAccountIdAndOtp(accountId, otp)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("auth.otp.invalid")));
    }

    @Override
    public void deleteById(Integer id) {
        forgotPasswordRepository.deleteById(id);
    }

    @Override
    public void saveByAccountIdAndDeleteAll(Long accountId, Integer otp, long now) {
        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.setOtp(otp);
        forgotPassword.setAccountId(accountId);
        forgotPassword.setExpiration(new Date(now + 2 * 60 * 1000));

        forgotPasswordRepository.deleteAllInBatch();

        forgotPasswordRepository.save(forgotPassword);
    }

}
