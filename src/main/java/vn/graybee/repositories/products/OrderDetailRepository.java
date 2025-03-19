package vn.graybee.repositories.products;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.graybee.models.products.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {


}
