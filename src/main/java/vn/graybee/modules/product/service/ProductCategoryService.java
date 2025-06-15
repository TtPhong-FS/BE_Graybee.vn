package vn.graybee.modules.product.service;

import vn.graybee.modules.catalog.dto.response.CategoryBasicDto;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;

import java.util.List;

public interface ProductCategoryService {

    void createProductCategoryByTags(Long productId, List<String> tags);

    void updateProductCategoryByTags(Long productId, List<String> tags);

    CategoryBasicDto findCategoryBasicDtoByProductId(long productId);

    List<ProductBasicResponse> findProductByTagSlug(String tagSlug);

}
