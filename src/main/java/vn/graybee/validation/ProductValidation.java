package vn.graybee.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantProduct;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.repositories.products.InventoryRepository;
import vn.graybee.repositories.products.ProductRepository;

@Service
public class ProductValidation {

    private static final Logger logger = LoggerFactory.getLogger(ProductValidation.class);

    private final InventoryRepository inventoryRepository;

    private final ProductRepository productRepository;

    public ProductValidation(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public void validateNameExists(String name) {
        if (productRepository.validateNameExists(name).isPresent()) {
            throw new BusinessCustomException(ConstantProduct.NAME, ConstantProduct.PRODUCT_NAME_ALREADY_EXISTS);
        }
    }


//    public ResponseEntity<BasicMessageResponse<Map<String, String>>> validationGeneralInfo(ProductValidationRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        try {
//            if (categoryRepository.validateNameExists(request.getCategoryName()).isEmpty()) {
//                throw new BusinessCustomException(ConstantCategory.CATEGORY_NAME, ConstantCategory.CATEGORY_DOES_NOT_EXIST);
//            }
//        } catch (BusinessCustomException e) {
//            errors.put(e.getField(), e.getMessage());
//        }
//
//        try {
//            if (manufacturerRepository.validateNameExists(request.getManufacturerName()).isEmpty()) {
//                throw new BusinessCustomException(ConstantCategory.MANUFACTURER_NAME, ConstantCategory.MANUFACTURER_DOES_NOT_EXIST);
//            }
//        } catch (BusinessCustomException e) {
//            errors.put(e.getField(), e.getMessage());
//        }
//
//        try {
//            validateNameExists(request.getProductName());
//        } catch (BusinessCustomException e) {
//            errors.put(e.getField(), e.getMessage());
//        }
//
//        if (!errors.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new BasicMessageResponse<>(400, "Dữ liệu không hợp lệ", errors)
//            );
//        }
//
//        return ResponseEntity.ok(new BasicMessageResponse<>(200, "Tất cả dữ liệu đều hợp lệ", null));
//    }

//    public void checkById(long productId) {
//        if(productRepository.checkById(productId)  null){
//            throw new   CustomNotFoundException(ConstantProduct.PRODUCT, ConstantProduct.PRODUCT_DOES_NOT_EXISTS));
//        }
//
//    }

    public long checkQuantityFromInventory(long id) {
        int quantity = productRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantProduct.GENERAL, ConstantProduct.PRODUCT_DOES_NOT_EXISTS));

        if (quantity > 0) {
            throw new BusinessCustomException(ConstantProduct.GENERAL, ConstantProduct.PRODUCT_STILL_INVENTORY);
        }
        
        return id;
    }

}
