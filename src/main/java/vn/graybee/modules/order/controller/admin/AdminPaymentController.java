package vn.graybee.modules.order.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.order.model.Payment;
import vn.graybee.modules.order.service.PaymentService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/orders/payments")
public class AdminPaymentController {

    private final PaymentService paymentService;

    private final MessageSourceUtil messageSourceUtil;

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<Payment>>> getAllPayment() {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        paymentService.getAllPayment(),
                        messageSourceUtil.get("order.payment.success.fetch.list")
                )
        );
    }

}
