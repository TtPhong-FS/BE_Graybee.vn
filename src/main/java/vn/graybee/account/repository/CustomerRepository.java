package vn.graybee.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.account.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Long> findIdByAccountId(Long accountId);

}
