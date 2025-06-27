package vn.graybee.modules.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryWithProducts {

    private String categoryName;

    private String categorySlug;

    private List<ProductBasicResponse> products;

}
