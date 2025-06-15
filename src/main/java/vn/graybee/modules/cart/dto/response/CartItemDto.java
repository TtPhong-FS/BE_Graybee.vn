package vn.graybee.modules.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private Integer cartItemId;

    private ProductBasicResponse product;

    private int quantity;

    private BigDecimal totalAmount;

}
