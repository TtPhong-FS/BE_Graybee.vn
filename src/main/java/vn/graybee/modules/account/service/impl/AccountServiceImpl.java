package vn.graybee.modules.account.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.auth.dto.response.AccountAuthDto;
import vn.graybee.auth.enums.Role;
import vn.graybee.auth.exception.AuthException;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.CodeGenerator;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.model.Account;
import vn.graybee.modules.account.repository.AccountRepository;
import vn.graybee.modules.account.service.AccountService;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final MessageSourceUtil messageSourceUtil;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void isAccountExistById(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new CustomNotFoundException(Constants.Common.root, messageSourceUtil.get("account.not.found"));
        }
    }

    @Override
    public void updatePasswordById(Long id, String password) {
        accountRepository.updatePasswordById(id, password);
    }

    @Override
    public Account saveAccount(CustomerRegisterRequest request) {

        String uid = CodeGenerator.generateCode(10, CodeGenerator.DIGITS);

        Account account = new Account();
        account.setUid(uid);
        account.setRole(Role.CUSTOMER);
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setActive(true);
        account.setSuperAdmin(false);
        account.setLastLoginAt(LocalDateTime.now());
        return accountRepository.save(account);
    }

    @Override
    public AccountAuthDto getAccountAuthDtoByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(Constants.Common.root, messageSourceUtil.get("auth.invalid_credentials")));
    }

    @Override
    public Long getAccountIdByEmail(String email) {
        return accountRepository.findIdByEmail(email).orElseThrow(() -> new BusinessCustomException(Constants.Common.root, messageSourceUtil.get("auth.mail.invalid")));
    }

    @Override
    public void checkExistsByEmail(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new BusinessCustomException(Constants.Auth.email, messageSourceUtil.get("account.email.exists"));
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateLastLoginAt(long id) {
        accountRepository.updateLastLoginAt(id);
    }

}
