package vn.graybee.modules.order.service;

import vn.graybee.modules.order.dto.request.PaymentCreateRequest;
import vn.graybee.modules.order.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    Payment savePaymentByOrderId(Long orderId, BigDecimal totalAmount, PaymentCreateRequest request);

    List<Payment> getAllPayment();

}
