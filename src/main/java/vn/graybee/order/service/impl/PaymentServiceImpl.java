package vn.graybee.order.service.impl;

import org.springframework.stereotype.Service;
import vn.graybee.order.dto.request.PaymentCreateRequest;
import vn.graybee.order.enums.PaymentMethod;
import vn.graybee.order.enums.PaymentStatus;
import vn.graybee.order.model.Payment;
import vn.graybee.order.service.PaymentService;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public Payment createPayment(Long orderId, BigDecimal totalAmount, PaymentCreateRequest request) {

        PaymentMethod paymentMethod = PaymentMethod.valueOf(request.getPaymentMethod());

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(PaymentStatus.UNPAID);

        payment.setTotalAmount(totalAmount);
        payment.setTransactionId(null);
        payment.setCurrentCode("VND");
        return payment;

    }

}
