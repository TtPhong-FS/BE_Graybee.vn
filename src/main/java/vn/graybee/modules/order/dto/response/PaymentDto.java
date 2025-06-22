package vn.graybee.modules.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.order.enums.PaymentMethod;
import vn.graybee.modules.order.enums.PaymentStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

}
