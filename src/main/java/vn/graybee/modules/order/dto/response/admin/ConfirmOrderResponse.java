package vn.graybee.modules.order.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.order.enums.OrderStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmOrderResponse {

    private long id;

    private OrderStatus status;


}
