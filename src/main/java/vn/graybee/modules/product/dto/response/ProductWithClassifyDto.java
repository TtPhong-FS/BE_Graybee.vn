package vn.graybee.modules.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.product.model.Product;
import vn.graybee.modules.product.model.ProductClassifyView;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithClassifyDto {

    private Product product;

    private ProductClassifyView classify;


}
