package vn.graybee.modules.account.service;

public interface CustomerService {

    Long getIdByAccountId(Long accountId);

    void saveCustomerByAccount(long accountId);

    void updateTotalSpentByAccountId(long accountId, double totalAmount);

    void updateTotalOrdersByAccountId(long accountId, long orders);

}
