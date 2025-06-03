package vn.graybee.modules.product.service;

import java.util.List;

public interface ProductImageService {

    void saveProductImages(Long productId, List<String> imageUrls);

    List<String> getProductImages(Long productId);

    void updateProductImages(Long productId, List<String> images);

}
