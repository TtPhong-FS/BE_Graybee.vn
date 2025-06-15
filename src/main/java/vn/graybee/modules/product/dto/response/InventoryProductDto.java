package vn.graybee.modules.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.product.enums.ProductStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryProductDto {

    private Long productId;

    private String productName;

    private ProductStatus productStatus;


}
