package vn.graybee.modules.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.order.dto.request.PaymentCreateRequest;
import vn.graybee.modules.order.enums.PaymentMethod;
import vn.graybee.modules.order.enums.PaymentStatus;
import vn.graybee.modules.order.model.Payment;
import vn.graybee.modules.order.repository.PaymentRepository;
import vn.graybee.modules.order.service.PaymentService;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Payment savePaymentByOrderId(Long orderId, BigDecimal totalAmount, PaymentCreateRequest request) {

        PaymentMethod paymentMethod = PaymentMethod.valueOf(request.getPaymentMethod());

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(PaymentStatus.UNPAID);

        payment.setTotalAmount(totalAmount);

        return paymentRepository.save(payment);


    }

    @Override
    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }

}
