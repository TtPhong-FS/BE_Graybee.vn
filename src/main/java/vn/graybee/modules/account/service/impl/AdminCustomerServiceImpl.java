package vn.graybee.modules.account.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.graybee.modules.account.dto.response.admin.CustomerResponse;
import vn.graybee.modules.account.repository.CustomerRepository;
import vn.graybee.modules.account.service.AdminCustomerService;

import java.util.List;

@AllArgsConstructor
@Service
public class AdminCustomerServiceImpl implements AdminCustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerResponse> getAllCustomer() {
        return customerRepository.findAllCustomer();
    }

}
