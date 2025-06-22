package vn.graybee.modules.order.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderTotalQuantityDto {

    private long orderId;

    private long totalQuantity;


}
