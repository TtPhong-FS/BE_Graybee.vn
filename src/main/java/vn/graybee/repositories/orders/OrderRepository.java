package vn.graybee.repositories.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.enums.OrderStatus;
import vn.graybee.models.orders.Order;
import vn.graybee.response.orders.OrderBasicDto;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("Select new vn.graybee.response.orders.OrderBasicDto(o.id, o.status, o.createdAt, o.totalAmount) from Order o  where (:status IS NULL OR o.status = :status and o.userUid = :userUid)")
    List<OrderBasicDto> findOrdersByUserUidAndStatusOptional(@Param("userUid") Integer userUid, @Param("status") OrderStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE Order o set o.isConfirmed = true, o.status = 'CONFIRMED' where o.id = :id")
    void confirmOrderById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query("Update Order o set o.isCancelled = true, o.status = 'CANCELLED' where o.id = :id and o.userUid = :userUid")
    void cancelOrderByIdAndUserId(@Param("id") long id, @Param("userUid") int userUid);

    @Query("Select o.status from Order o where o.id = :id")
    OrderStatus findStatusById(long id);

    @Query("Select o.id from Order o where o.sessionId = :sessionId")
    List<Long> findIdsBySessionId(String sessionId);

    @Transactional
    @Modifying
    @Query("Update Order o set o.userUid = :userUid, o.sessionId = null where o.id in :ids")
    void transformOrdersToUserUidByIds(@Param("ids") List<Long> ids, @Param("userUid") Integer userUid);

    @Query("SELECT exists (Select 1 FROM Order WHERE id = :id)")
    boolean checkExistsById(long id);

}
