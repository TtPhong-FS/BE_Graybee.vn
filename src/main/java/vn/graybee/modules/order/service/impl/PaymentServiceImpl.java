package vn.graybee.modules.order.service.impl;

import org.springframework.stereotype.Service;
import vn.graybee.modules.order.dto.request.PaymentCreateRequest;
import vn.graybee.modules.order.enums.PaymentMethod;
import vn.graybee.modules.order.enums.PaymentStatus;
import vn.graybee.modules.order.model.Payment;
import vn.graybee.modules.order.service.PaymentService;

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
