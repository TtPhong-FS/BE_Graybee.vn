package vn.graybee.modules.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.modules.account.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Long> findIdByAccountId(Long accountId);

}
