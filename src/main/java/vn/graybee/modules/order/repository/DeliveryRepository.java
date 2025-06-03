package vn.graybee.modules.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.modules.order.dto.response.user.OrderDeliveryTypeDto;
import vn.graybee.modules.order.model.Delivery;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    @Query("Select new vn.graybee.modules.order.dto.response.user.OrderDeliveryTypeDto(o.id, d.deliveryType) from Delivery d join Order o on d.orderId = o.id where d.orderId IN :orderIds ORDER BY o.id")
    List<OrderDeliveryTypeDto> findDeliveryTypeByOrderIds(@Param("orderIds") List<Long> orderIds);

}
