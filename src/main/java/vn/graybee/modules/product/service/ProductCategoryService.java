package vn.graybee.modules.product.service;

import vn.graybee.modules.catalog.dto.response.CategoryBasicDto;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;

import java.util.List;

public interface ProductCategoryService {

    List<ProductBasicResponse> findProductByCategorySlug(String categorySlug);

    void createProductCategory(Long productId, String categoryName, String brand, List<String> tags);

    void updateProductCategory(Long productId, String brand, List<String> tags);

    CategoryBasicDto findCategoryBasicDtoByProductId(long productId);

}
