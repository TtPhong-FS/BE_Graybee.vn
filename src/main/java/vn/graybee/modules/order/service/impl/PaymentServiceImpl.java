package vn.graybee.modules.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public void savePaymentByOrderId(Long orderId, BigDecimal totalAmount, String paymentMethod) {

        PaymentMethod paymentMe = PaymentMethod.valueOf(paymentMethod);

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPaymentMethod(paymentMe);
        payment.setPaymentStatus(PaymentStatus.UNPAID);
        payment.setTotalAmount(totalAmount);

        paymentRepository.save(payment);

    }

    @Override
    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }

}
