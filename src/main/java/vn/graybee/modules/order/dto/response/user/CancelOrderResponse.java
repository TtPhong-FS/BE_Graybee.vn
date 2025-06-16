package vn.graybee.modules.order.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.order.enums.OrderStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderResponse {

    private String code;

    private OrderStatus status;

}
