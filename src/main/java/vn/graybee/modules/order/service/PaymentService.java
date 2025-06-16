package vn.graybee.modules.order.service;

import vn.graybee.modules.order.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    void savePaymentByOrderId(Long orderId, BigDecimal totalAmount, String paymentMethod);

    List<Payment> getAllPayment();

}
