package vn.graybee.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.graybee.constants.categories.ErrorCategoryConstants;
import vn.graybee.constants.categories.ErrorManufacturerConstants;
import vn.graybee.constants.products.ErrorProductConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.repositories.categories.CategoryRepository;
import vn.graybee.repositories.categories.ManufacturerRepository;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.requests.products.ProductValidationRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductValidation {

    private static final Logger logger = LoggerFactory.getLogger(ProductValidation.class);

    private final ProductRepository productRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final CategoryRepository categoryRepository;

    public ProductValidation(ProductRepository productRepository, ManufacturerRepository manufacturerRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.categoryRepository = categoryRepository;
    }

    public void validateNameExists(String name) {
        if (productRepository.validateNameExists(name).isPresent()) {
            logger.info("Product was exists with name " + name);
            throw new BusinessCustomException(ErrorProductConstants.NAME, ErrorProductConstants.PRODUCT_NAME_ALREADY_EXISTS);
        }
    }

    public void validateModelExists(String model) {
        if (productRepository.validateModelExists(model).isPresent()) {
            logger.info("Product was exists with model " + model);
            throw new BusinessCustomException(ErrorProductConstants.MODEL, ErrorProductConstants.PRODUCT_MODEL_ALREADY_EXISTS);
        }
    }

    public ResponseEntity<BasicMessageResponse<Map<String, String>>> validationGeneralInfo(ProductValidationRequest request) {
        Map<String, String> errors = new HashMap<>();
        try {
            if (categoryRepository.validateNameExists(request.getCategoryName()).isEmpty()) {
                throw new BusinessCustomException(ErrorCategoryConstants.NAME_ERROR, ErrorCategoryConstants.CATEGORY_DOES_NOT_EXIST);
            }
        } catch (BusinessCustomException e) {
            errors.put(e.getField(), e.getMessage());
        }

        try {
            if (manufacturerRepository.validateNameExists(request.getManufacturerName()).isEmpty()) {
                throw new BusinessCustomException(ErrorManufacturerConstants.NAME_ERROR, ErrorManufacturerConstants.MANUFACTURER_DOES_NOT_EXIST);
            }
        } catch (BusinessCustomException e) {
            errors.put(e.getField(), e.getMessage());
        }

        try {
            validateNameExists(request.getName());
        } catch (BusinessCustomException e) {
            errors.put(e.getField(), e.getMessage());
        }

        try {
            validateModelExists(request.getModel());
        } catch (BusinessCustomException e) {
            errors.put(e.getField(), e.getMessage());
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BasicMessageResponse<>(400, "Dữ liệu không hợp lệ", errors)
            );
        }

        return ResponseEntity.ok(new BasicMessageResponse<>(200, "Tất cả dữ liệu đều hợp lệ", Collections.emptyMap()));
    }

}
