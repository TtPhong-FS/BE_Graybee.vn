package vn.graybee.modules.product.service;

public interface ProductDescriptionService {

    void saveProductDescription(Long productId, String description);

    String getProductDescription(Long productId);

    void updateProductDescription(Long productId, String description);

}
