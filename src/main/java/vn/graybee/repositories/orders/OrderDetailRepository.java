package vn.graybee.repositories.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.orders.OrderDetail;
import vn.graybee.response.orders.OrderMapOrderDetailWithProductBasicDto;
import vn.graybee.response.orders.OrderTotalQuantityDto;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("Select p.finalPrice from Product p where p.id = :productId ")
    double findProductFinalPriceByProductId(@Param("productId") long productId);

    @Query("SELECT new vn.graybee.response.orders.OrderMapOrderDetailWithProductBasicDto(od.orderId, od.id, p.name, p.thumbnail, od.quantity, od.subtotal, od.priceAtTime) " +
            "FROM OrderDetail od JOIN Product p ON od.productId = p.id " +
            "WHERE od.orderId IN :orderIds " +
            "ORDER BY od.orderId, od.id ")
    List<OrderMapOrderDetailWithProductBasicDto> getOrderDetailWithProductBasicByOrderIds(@Param("orderIds") List<Long> orderIds);

    @Query("SELECT new vn.graybee.response.orders.OrderTotalQuantityDto(od.orderId, SUM(od.quantity)) FROM OrderDetail od WHERE od.orderId IN :orderIds GROUP BY od.orderId")
    List<OrderTotalQuantityDto> getTotalQuantityByOrderIds(@Param("orderIds") List<Long> orderIds);

}
