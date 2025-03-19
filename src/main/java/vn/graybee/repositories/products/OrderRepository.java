package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.products.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
