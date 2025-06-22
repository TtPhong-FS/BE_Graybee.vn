package vn.graybee.modules.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.account.dto.response.admin.CustomerResponse;
import vn.graybee.modules.account.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("Select c.accountId from Customer c where c.accountId = :accountId")
    Optional<Long> findIdByAccountId(Long accountId);


    @Transactional
    @Modifying
    @Query("Update Customer c set c.totalSpent = c.totalSpent + :totalAmount where c.accountId = :accountId")
    void updateTotalSpent(long accountId, double totalAmount);

    @Transactional
    @Modifying
    @Query("Update Customer c set c.totalOrders = c.totalOrders + :orders where c.accountId = :accountId")
    void updateTotalOrders(long accountId, long orders);

    @Query("""
            Select new vn.graybee.modules.account.dto.response.admin.CustomerResponse(c.accountId, p.fullName,a.email, c.totalOrders, c.totalSpent, c.lastOrderAt)
            from Customer c
            join Profile p on p.accountId = c.accountId
            join Account a on c.accountId = a.id
            """)
    List<CustomerResponse> findAllCustomer();

}
