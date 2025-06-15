package vn.graybee.modules.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.order.dto.response.user.OrderDeliveryTypeDto;
import vn.graybee.modules.order.enums.DeliveryStatus;
import vn.graybee.modules.order.model.Delivery;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query("Select new vn.graybee.modules.order.dto.response.user.OrderDeliveryTypeDto(o.id, d.deliveryType) from Delivery d join Order o on d.orderId = o.id where d.orderId IN :orderIds ORDER BY o.id")
    List<OrderDeliveryTypeDto> findDeliveryTypeByOrderIds(@Param("orderIds") List<Long> orderIds);

    @Query("Select exists (Select 1 from Delivery d where d.id = :id)")
    boolean existsById(long id);

    @Transactional
    @Modifying
    @Query("Update Delivery d set d.status = :deliveryStatus where d.id = :id")
    void updateStatusByIdAndStatus(long id, DeliveryStatus deliveryStatus);

}
