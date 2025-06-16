package vn.graybee.modules.account.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.model.Customer;
import vn.graybee.modules.account.repository.CustomerRepository;
import vn.graybee.modules.account.service.AccountService;
import vn.graybee.modules.account.service.CustomerService;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final AccountService accountService;

    private final MessageSourceUtil messageSourceUtil;


    @Override
    public Long getIdByAccountId(Long accountId) {
        accountService.isAccountExistById(accountId);

        return customerRepository.findIdByAccountId(accountId)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("account.customer.not.found")));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveCustomerByAccount(long accountId) {
        Customer customer = new Customer();
        customer.setAccountId(accountId);
        customer.setTotalSpent(0);
        customer.setTotalOrders(0);
        customer.setLastOrderAt(LocalDateTime.now());

        customerRepository.save(customer);
    }

}
