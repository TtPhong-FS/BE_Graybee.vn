package vn.graybee.modules.order.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMapOrderDetailWithProductBasicDto {

    private long orderId;

    private long orderDetailId;

    private long productId;

    private String name;

    private String thumbnail;

    private int quantity;

    private double subtotal;

    private double priceAtTime;


}
