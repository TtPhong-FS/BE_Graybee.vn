package vn.graybee.validation;

import org.springframework.stereotype.Service;
import vn.graybee.constants.products.ErrorProductConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.repositories.business.ProductRepository;

@Service
public class ProductValidation {

    private final ProductRepository productRepository;

    public ProductValidation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void ensureProductNameBeforeCreate(String productName) {
        if (productRepository.ensureProductNameBeforeCreate(productName).isPresent()) {
            throw new BusinessCustomException(ErrorProductConstants.NAME_ERROR, ErrorProductConstants.PRODUCT_NAME_ALREADY_EXISTS);
        }
    }

    public void checkProductModelExists(String model) {
        if (productRepository.checkProductModelExists(model).isPresent()) {
            throw new BusinessCustomException(ErrorProductConstants.MODEL_ERROR, ErrorProductConstants.PRODUCT_MODEL_ALREADY_EXISTS);
        }
    }

}
