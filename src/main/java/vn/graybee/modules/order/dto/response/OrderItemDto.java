package vn.graybee.modules.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private long orderDetailId;

    private int quantity;

    private double subtotal;

    private double priceAtTime;

    private Long productId;

    private String productName;

    private String slug;

    private String thumbnail;

}
