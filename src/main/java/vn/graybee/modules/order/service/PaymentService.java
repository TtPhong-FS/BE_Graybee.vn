package vn.graybee.modules.order.service;

import vn.graybee.modules.order.dto.response.PaymentDto;
import vn.graybee.modules.order.model.Payment;

import java.util.List;

public interface PaymentService {

    void savePaymentByOrderId(Long orderId, double totalAmount, String paymentMethod);

    List<Payment> getAllPayment();

    PaymentDto findPaymentDtoByOrderId(long orderId);

}
