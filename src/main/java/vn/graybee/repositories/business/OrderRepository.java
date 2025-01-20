package vn.graybee.repositories.business;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.business.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
