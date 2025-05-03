package vn.graybee.services.users;

import vn.graybee.messages.MessageResponse;
import vn.graybee.response.admin.users.AdminCustomerResponse;

import java.util.List;

public interface CustomerService {

    MessageResponse<List<AdminCustomerResponse>> getAllCustomers();

}
