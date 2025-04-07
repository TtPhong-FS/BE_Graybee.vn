package vn.graybee.repositories.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.orders.Order;
import vn.graybee.response.orders.OrderBasicDto;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("Select new vn.graybee.response.orders.OrderBasicDto(o.id, o.status, o.createdAt, o.totalAmount) from Order o where o.userUid = :userUid ")
    List<OrderBasicDto> findOrdersBasicByUserUid(@Param("userUid") int userUid);

    @Query("Select new vn.graybee.response.orders.OrderBasicDto(o.id, o.status, o.createdAt, o.totalAmount) from Order o where o.userUid = :userUid and o.status = :status")
    List<OrderBasicDto> findOrdersBasicByUserUidAndStatus(@Param("userUid") int userUid, @Param("status") String status);

}
