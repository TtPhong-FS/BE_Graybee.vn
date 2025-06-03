package vn.graybee.modules.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.modules.order.dto.response.user.OrderBasicDto;
import vn.graybee.modules.order.enums.OrderStatus;
import vn.graybee.modules.order.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT new vn.graybee.modules.order.dto.response.admin.AdminOrderResponse(
                o.id,
                o.totalAmount,
                pro.phone,
                pro.fullName,
                pro.avatarUrl,
                pay.paymentStatus,
                d.deliveryType,
                o.status,
                o.isCancelled,
                o.isConfirmed,
                o.createdAt
            )
            FROM Order o
            JOIN Profile pro on pro.accountId = o.accountId
            LEFT JOIN Payment pay ON pay.orderId = o.id
            LEFT JOIN Delivery d ON d.orderId = o.id
            """)
    Page<AdminOrderResponse> fetchAll(Pageable pageable);

    @Query("Select new vn.graybee.modules.order.dto.response.user.OrderBasicDto(o.id, o.status, o.createdAt, o.totalAmount) from Order o  where (:status IS NULL OR o.status = :status and o.accountId = :accountId)")
    List<OrderBasicDto> findAllOrdersByAccountIdAndStatus(@Param("accountId") Long accountId, @Param("status") OrderStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE Order o set o.isConfirmed = true, o.status = 'CONFIRMED' where o.id = :id")
    void confirmOrderById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query("Update Order o set o.isCancelled = true, o.status = 'CANCELLED' where o.id = :id")
    void cancelOrderById(@Param("id") long id);

    @Query("Select o.status from Order o where o.id = :id")
    OrderStatus findStatusById(long id);

    @Query("Select o.id from Order o where o.sessionId = :sessionId")
    List<Long> findIdsBySessionId(String sessionId);

    @Transactional
    @Modifying
    @Query("Update Order o set o.accountId = :accountId, o.sessionId = null where o.id in :ids")
    void transformOrdersToAccountByIds(@Param("accountId") Long accountId, @Param("ids") List<Long> ids);

    @Query("SELECT exists (Select 1 FROM Order WHERE id = :id)")
    boolean checkExistsById(long id);

}
