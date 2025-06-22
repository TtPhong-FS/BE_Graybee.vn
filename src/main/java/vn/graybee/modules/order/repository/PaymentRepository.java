package vn.graybee.modules.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.modules.order.dto.response.PaymentDto;
import vn.graybee.modules.order.dto.response.user.OrderPaymentMethodDto;
import vn.graybee.modules.order.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("Select new vn.graybee.modules.order.dto.response.user.OrderPaymentMethodDto(o.id, p.paymentMethod) from Payment p join Order o on p.orderId = o.id where p.orderId IN :orderIds")
    List<OrderPaymentMethodDto> getPaymentMethodByOrderIds(@Param("orderIds") List<Long> orderIds);

    @Query("""
            Select new vn.graybee.modules.order.dto.response.PaymentDto(p.paymentMethod, p.paymentStatus)
            from Payment p
            where p.orderId = :orderId
            """)
    Optional<PaymentDto> findPaymentDtoByOrderId(long orderId);

}
