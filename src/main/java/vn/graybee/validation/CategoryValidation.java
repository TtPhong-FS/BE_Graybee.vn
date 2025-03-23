package vn.graybee.validation;

import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.repositories.categories.CategoryRepository;
import vn.graybee.repositories.products.ProductRepository;

@Service
public class CategoryValidation {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    public CategoryValidation(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public int getIdByName(String categoryName) {
        return categoryRepository.getIdByName(categoryName)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_TEMPORARILY_FLAGGED));
    }


    public void countProductById(int id) {
        int products = categoryRepository.getCountProductById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_DOES_NOT_EXIST));
        if (products > 0) {
            throw new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_ID_USED_IN_PRODUCT);
        }
    }

    public void validateNameExists(String name) {
        if (categoryRepository.validateNameExists(name).isEmpty()) {
            throw new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_DOES_NOT_EXIST);
        }
    }

    public void checkExistByName(String name) {
        if (categoryRepository.validateNameExists(name).isPresent()) {
            throw new BusinessCustomException(ConstantCategory.CATEGORY_NAME, ConstantCategory.CATEGORY_NAME_EXISTS);
        }
    }

}
