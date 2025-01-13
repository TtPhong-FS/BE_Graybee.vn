package vn.graybee.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
