package vn.graybee.modules.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private Long cartItemId;

    private ProductBasicResponse product;

    private int quantity;

    private double totalAmount;

}
