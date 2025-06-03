package vn.graybee.modules.account.service.impl;

import org.springframework.stereotype.Service;
import vn.graybee.common.constants.ConstantAccount;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.repository.CustomerRepository;
import vn.graybee.modules.account.service.AccountService;
import vn.graybee.modules.account.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final AccountService accountService;

    private final MessageSourceUtil messageSourceUtil;

    public CustomerServiceImpl(CustomerRepository customerRepository, AccountService accountService, MessageSourceUtil messageSourceUtil) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
        this.messageSourceUtil = messageSourceUtil;
    }

    @Override
    public Long getIdByAccountId(Long accountId) {
        accountService.isAccountExistById(accountId);

        return customerRepository.findIdByAccountId(accountId)
                .orElseThrow(() -> new CustomNotFoundException(ConstantAccount.CUSTOMER_NOT_FOUND, messageSourceUtil.get("common.not.found", new Object[]{ConstantAccount.CUSTOMER})));
    }

}
