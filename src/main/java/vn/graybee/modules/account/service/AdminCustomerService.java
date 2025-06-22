package vn.graybee.modules.account.service;

import vn.graybee.modules.account.dto.response.admin.CustomerResponse;

import java.util.List;

public interface AdminCustomerService {

    List<CustomerResponse> getAllCustomer();

}
