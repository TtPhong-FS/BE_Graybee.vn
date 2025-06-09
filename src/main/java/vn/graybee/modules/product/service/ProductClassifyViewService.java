package vn.graybee.modules.product.service;

import vn.graybee.modules.product.model.ProductClassifyView;

import java.util.List;

public interface ProductClassifyViewService {

    ProductClassifyView createProductClassifyView(Long productId, String productName, String productSlug, String categoryName, String brandName, List<String> tagNames);

    ProductClassifyView updateProductClassifyViewByProductId(Long productId, String productName, String productSlug, String brandName, List<String> tagNames);

    void removeCategoryByCategoryName(String categoryName);

    void removeBrandByBrandName(String brandName);

    void removeTagByTagName(String tagName);

    ProductClassifyView findByProductId(long productId);

}
