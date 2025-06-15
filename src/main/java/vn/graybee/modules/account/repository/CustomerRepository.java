package vn.graybee.modules.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.graybee.modules.account.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("Select c.id from Customer c where c.accountId = :accountId")
    Optional<Long> findIdByAccountId(Long accountId);

}
