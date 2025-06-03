package vn.graybee.modules.account.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vn.graybee.auth.dto.request.CustomerRegisterRequest;
import vn.graybee.auth.dto.response.AccountAuthDto;
import vn.graybee.auth.enums.Role;
import vn.graybee.auth.exception.AuthException;
import vn.graybee.common.constants.ConstantAccount;
import vn.graybee.common.constants.ConstantAuth;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.CodeGenerator;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.model.Account;
import vn.graybee.modules.account.repository.AccountRepository;
import vn.graybee.modules.account.service.AccountService;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final MessageSourceUtil messageSourceUtil;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void isAccountExistById(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new CustomNotFoundException(ConstantAccount.ACCOUNT_NOT_FOUND, messageSourceUtil.get("common.not.found", new Object[]{ConstantAccount.ACCOUNT}));
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
        account.setUsername(request.getPhone());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setActive(true);
        account.setSuperAdmin(false);
        return accountRepository.save(account);
    }

    @Override
    public AccountAuthDto getAccountAuthDtoByUsername(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException(ConstantAuth.AUTH_USER_NOT_FOUND, messageSourceUtil.get("auth.user.invalid_credentials")));
    }

}
