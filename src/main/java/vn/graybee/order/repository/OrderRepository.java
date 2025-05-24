package vn.graybee.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.order.dto.response.admin.AdminCustomerOrderResponse;
import vn.graybee.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.order.dto.response.user.OrderBasicDto;
import vn.graybee.order.enums.OrderStatus;
import vn.graybee.order.model.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("Select new vn.graybee.order.dto.response.admin.AdminOrderResponse(o.id, o.userUid, o.totalAmount, a.phoneNumber, a.fullName, p.paymentStatus, d.deliveryType, o.status, o.isCancelled, o.isConfirmed, o.createdAt) from Order o " +
            "LEFT JOIN Address a on o.addressId = a.id " +
            "LEFT JOIN Payment p on p.orderId = o.id " +
            "LEFT JOIN Delivery d on d.orderId = o.id ")
    Page<AdminOrderResponse> fetchAll(Pageable pageable);

    @Query("Select new vn.graybee.order.dto.response.user.OrderBasicDto(o.id, o.status, o.createdAt, o.totalAmount) from Order o  where (:status IS NULL OR o.status = :status and o.userUid = :userUid)")
    List<OrderBasicDto> findOrdersByUserUidAndStatusOptional(@Param("userUid") String userUid, @Param("status") OrderStatus status);

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
    void transformOrdersToAccountByIds(@Param("ids") List<Long> ids, @Param("accountId") Long accountId);

    @Query("SELECT exists (Select 1 FROM Order WHERE id = :id)")
    boolean checkExistsById(long id);

    @Query("Select new vn.graybee.order.dto.response.admin.AdminCustomerOrderResponse(SUM(o.totalAmount), COUNT(o.userUid)) from Order o where o.userUid = :userUid")
    AdminCustomerOrderResponse findCustomerOrderByUserUId(Integer userUid);

    @Query("Select o.createdAt from Order o where o.userUid = :userUid order by o.createdAt desc limit 1")
    LocalDateTime findLastOrderDateByUid(Integer userUid);

}
