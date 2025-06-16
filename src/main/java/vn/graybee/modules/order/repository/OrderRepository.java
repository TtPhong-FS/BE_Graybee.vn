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
import vn.graybee.modules.order.dto.response.user.OrderHistoryResponse;
import vn.graybee.modules.order.enums.OrderStatus;
import vn.graybee.modules.order.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT new vn.graybee.modules.order.dto.response.admin.AdminOrderResponse(
                o.id,
                o.totalAmount,
                d.recipientName,
                d.recipientPhone,
                pro.avatarUrl,
                pay.paymentStatus,
                d.deliveryType,
                o.status,
                o.createdAt
            )
            FROM Order o
            LEFT JOIN Profile pro on pro.accountId = o.accountId
            LEFT JOIN Payment pay ON pay.orderId = o.id
            LEFT JOIN Delivery d ON d.orderId = o.id
            """)
    Page<AdminOrderResponse> fetchAll(Pageable pageable);

    @Query("Select new vn.graybee.modules.order.dto.response.user.OrderBasicDto(o.id, o.status, o.createdAt, o.totalAmount) from Order o  where (:status IS NULL OR o.status = :status and o.accountId = :accountId)")
    List<OrderBasicDto> findAllOrdersByAccountIdAndStatus(@Param("accountId") Long accountId, @Param("status") OrderStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE Order o set o.status = :status where o.id = :id")
    void updateStatusByIdAndStatus(@Param("id") long id, @Param("status") OrderStatus status);

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

    @Query("""
            SELECT new vn.graybee.modules.order.dto.response.user.OrderHistoryResponse(
              o.code,
              o.status,
              o.totalAmount,
              o.createdAt,
              COALESCE(d.deliveryType, 'HOME_DELIVERY'),
              COALESCE(p.paymentMethod, 'COD'),
              COALESCE(p.paymentStatus, 'UNPAID'),
             COALESCE(d.shippingMethod, 'STANDARD')
            )
            FROM Order o
            LEFT JOIN Delivery d on d.orderId = o.id
            LEFT JOIN Payment p on p.orderId = o.id
            where o.accountId = :accountId
            """)
    List<OrderHistoryResponse> findAllOrderHistoryResponseByAccountId(Long accountId);

    @Query("Select o.id from Order o where o.code = :code")
    Optional<Long> findIdByCode(String code);

}
