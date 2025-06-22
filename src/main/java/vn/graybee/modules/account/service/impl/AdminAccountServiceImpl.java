package vn.graybee.modules.account.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.auth.enums.Role;
import vn.graybee.auth.record.MailBody;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.service.MailService;
import vn.graybee.common.utils.CodeGenerator;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.dto.request.admin.AccountCreateRequest;
import vn.graybee.modules.account.dto.request.admin.ChangeUsernameRequest;
import vn.graybee.modules.account.dto.response.admin.AccountAuthResponse;
import vn.graybee.modules.account.dto.response.admin.AccountIdActiveResponse;
import vn.graybee.modules.account.dto.response.admin.UsernameResponse;
import vn.graybee.modules.account.model.Account;
import vn.graybee.modules.account.repository.AccountRepository;
import vn.graybee.modules.account.service.AdminAccountService;
import vn.graybee.modules.account.service.CustomerService;
import vn.graybee.modules.account.service.ProfileService;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class AdminAccountServiceImpl implements AdminAccountService {

    private final AccountRepository accountRepository;

    private final ProfileService profileService;

    private final CustomerService customerService;


    private final MessageSourceUtil messageSourceUtil;

    private final BCryptPasswordEncoder passwordEncoder;

    private final MailService mailService;

    @Override
    public List<AccountAuthResponse> getAllAccountDto() {
        return accountRepository.findAllAccountAuthDto();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AccountIdActiveResponse toggleActiveAccountById(long id) {

        Boolean active = accountRepository.getActiveById(id).
                orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, "Tài khoản không tồn tại"));

        accountRepository.toggleActiveById(id);

        return new AccountIdActiveResponse(id, !active);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AccountAuthResponse createAccount(AccountCreateRequest request) {

        Role role = Role.fromString(request.getRole(), messageSourceUtil);

        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new BusinessCustomException(Constants.Auth.email, messageSourceUtil.get("account.email.exists"));
        }

        String uid = CodeGenerator.generateCode(10, CodeGenerator.DIGITS);

        Account account = new Account();

        account.setEmail(request.getEmail());
        account.setPassword(request.getPassword());
        account.setActive(request.isActive());
        account.setRole(role);
        account.setLastLoginAt(LocalDateTime.now());
        account.setUid(uid);
        account = accountRepository.save(account);

        profileService.saveProfileByAccountId(account.getId(), request.getProfile());

        if (role == Role.CUSTOMER) {
            customerService.saveCustomerByAccount(account.getId());
        }

        return new AccountAuthResponse(account.getId(), account.getUid(), account.getEmail(), account.getRole(), account.isActive(), account.getLastLoginAt());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void resetPassword(long accountId) {
        checkExistsById(accountId);

        String email = accountRepository.findEmailById(accountId)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("account.not.found")));

        String newPassword = CodeGenerator.generateCode(12, CodeGenerator.ALPHANUMERIC);

        MailBody mailBody = new MailBody(
                email,
                "Yêu cầu đặt lại mật khẩu",
                "Mật khẩu của bạn đã được đặt lại thành công, hãy đăng nhập bằng mật khẩu mới là: " + newPassword);

        mailService.sendMail(mailBody);

        accountRepository.updatePasswordById(accountId, passwordEncoder.encode(newPassword));
    }

    private void checkExistsById(long id) {
        if (!accountRepository.existsById(id)) {
            throw new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("account.not.found"));
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String changeUsername(long id, ChangeUsernameRequest request) {
        checkExistsById(id);

        accountRepository.changeUsername(id, request.getNewUsername());

        return request.getNewUsername();
    }

    @Override
    public UsernameResponse getUsernameById(long id) {
        String username = accountRepository.findEmailById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("account.not.found")));

        return new UsernameResponse(username);
    }

}
