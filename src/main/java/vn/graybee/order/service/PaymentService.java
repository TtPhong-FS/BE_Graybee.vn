package vn.graybee.order.service;

import vn.graybee.order.dto.request.PaymentCreateRequest;
import vn.graybee.order.model.Payment;

import java.math.BigDecimal;

public interface PaymentService {

    Payment createPayment(Long orderId, BigDecimal totalAmount, PaymentCreateRequest request);

}
