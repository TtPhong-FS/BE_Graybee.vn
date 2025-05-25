package vn.graybee.account.service.impl;

import org.springframework.stereotype.Service;
import vn.graybee.account.repository.AccountRepository;
import vn.graybee.account.service.AccountService;
import vn.graybee.common.constants.ConstantAccount;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final MessageSourceUtil messageSourceUtil;

    public AccountServiceImpl(AccountRepository accountRepository, MessageSourceUtil messageSourceUtil) {
        this.accountRepository = accountRepository;
        this.messageSourceUtil = messageSourceUtil;
    }

    @Override
    public void isAccountExistById(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new CustomNotFoundException(ConstantAccount.ACCOUNT_NOT_FOUND, messageSourceUtil.get("common.not.found", new Object[]{ConstantAccount.ACCOUNT}));
        }
    }

}
