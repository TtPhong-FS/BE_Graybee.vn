package vn.graybee.modules.product.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.product.model.ProductDescription;
import vn.graybee.modules.product.repository.ProductDescriptionRepository;
import vn.graybee.modules.product.service.ProductDescriptionService;

@Service
public class ProductDescriptionServiceImpl implements ProductDescriptionService {

    private final ProductDescriptionRepository productDescriptionRepository;

    public ProductDescriptionServiceImpl(ProductDescriptionRepository productDescriptionRepository) {
        this.productDescriptionRepository = productDescriptionRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveProductDescription(Long productId, String description) {
        ProductDescription productDescription = new ProductDescription();

        productDescription.setDescription(description != null && !description.isEmpty() ? description : null);
        productDescription.setProductId(productId);

        productDescriptionRepository.save(productDescription);
    }

    @Override
    public String getProductDescription(Long productId) {
        return productDescriptionRepository.findDescriptionByProductId(productId).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateProductDescription(Long productId, String description) {
        ProductDescription productDescription = productDescriptionRepository.findByProductId(productId)
                .orElseGet(ProductDescription::new);

        boolean isDescriptionExists = productDescription.getProductId() == null;

        if (isDescriptionExists) {
            productDescription.setProductId(productId);
        }

        productDescription.setDescription(description != null && !description.isEmpty() ? description : null);

        productDescriptionRepository.save(productDescription);
    }

}
