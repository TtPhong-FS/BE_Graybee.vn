package vn.graybee.modules.order.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.order.enums.PaymentMethod;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentMethodDto {

    private long orderId;

    private PaymentMethod paymentMethod;

}
