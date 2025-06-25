package vn.graybee.modules.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.modules.order.dto.response.OrderItemDto;
import vn.graybee.modules.order.dto.response.user.OrderMapOrderDetailWithProductBasicDto;
import vn.graybee.modules.order.dto.response.user.OrderTotalQuantityDto;
import vn.graybee.modules.order.model.OrderDetail;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("SELECT new vn.graybee.modules.order.dto.response.user.OrderMapOrderDetailWithProductBasicDto(od.orderId, od.id,p.id, p.name, p.thumbnail, od.quantity, od.subtotal, od.priceAtTime) " +
            "FROM OrderDetail od JOIN Product p ON od.productId = p.id " +
            "WHERE od.orderId IN :orderIds " +
            "ORDER BY od.orderId, od.id ")
    List<OrderMapOrderDetailWithProductBasicDto> getOrderDetailWithProductBasicByOrderIds(@Param("orderIds") List<Long> orderIds);

    @Query("SELECT new vn.graybee.modules.order.dto.response.user.OrderTotalQuantityDto(od.orderId, SUM(od.quantity)) FROM OrderDetail od WHERE od.orderId IN :orderIds GROUP BY od.orderId")
    List<OrderTotalQuantityDto> getTotalQuantityByOrderIds(@Param("orderIds") List<Long> orderIds);

    @Query("Select od from OrderDetail od where od.orderId = :orderId")
    List<OrderDetail> findAllByOrderId(long orderId);

    @Query("""
            Select new vn.graybee.modules.product.dto.response.ProductBasicResponse(p.id, p.name, p.slug, p.price, p.finalPrice, p.thumbnail)
            from OrderDetail od
            join Product p on p.id = od.productId
            join Order o on o.id = od.orderId
            where o.code = :orderCode
            """)
    List<ProductBasicResponse> findProductsExistByOrderCode(String orderCode);

    @Query("""
            Select new vn.graybee.modules.order.dto.response.OrderItemDto(od.id, od.quantity, od.subtotal,od.priceAtTime, p.id, p.name, p.slug, p.thumbnail)
            from OrderDetail od
            join Product p on p.id = od.productId
            where od.orderId = :orderId
            """)
    List<OrderItemDto> findItemByOrderId(long orderId);

}
