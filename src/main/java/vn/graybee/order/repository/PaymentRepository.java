package vn.graybee.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.order.dto.response.user.OrderPaymentMethodDto;
import vn.graybee.order.model.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("Select new vn.graybee.order.dto.response.user.OrderPaymentMethodDto(o.id, p.paymentMethod) from Payment p join Order o on p.orderId = o.id where p.orderId IN :orderIds")
    List<OrderPaymentMethodDto> getPaymentMethodByOrderIds(@Param("orderIds") List<Long> orderIds);

}
