package vn.graybee.account.controller;

import vn.graybee.customer.service.CustomerService;

public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

}
