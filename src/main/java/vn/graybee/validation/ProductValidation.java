package vn.graybee.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.graybee.constants.products.ErrorProductConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.repositories.business.ProductRepository;

@Service
public class ProductValidation {

    private static final Logger logger = LoggerFactory.getLogger(ProductValidation.class);

    private final ProductRepository productRepository;

    public ProductValidation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateNameExists(String name){
       if(productRepository.validateNameExists(name).isPresent()){
           logger.info("Product was exists with name " + name);
           throw new BusinessCustomException(ErrorProductConstants.NAME, ErrorProductConstants.PRODUCT_NAME_ALREADY_EXISTS);
       }
    }

    public void validateModelExists(String model){
        if(productRepository.validateModelExists(model).isPresent()){
            logger.info("Product was exists with model " + model);
            throw new BusinessCustomException(ErrorProductConstants.MODEL, ErrorProductConstants.MODEL);
        }
    }
}
