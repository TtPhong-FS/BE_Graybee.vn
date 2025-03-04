package vn.graybee.validation;

import org.springframework.stereotype.Service;
import vn.graybee.constants.categories.ErrorCategoryConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.models.categories.Category;
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

    public Category validateCategoryExistsByName(String categoryName) {
        Category category = categoryRepository.findToCreateProduct(categoryName)
                .orElseThrow(() -> new CustomNotFoundException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_DOES_NOT_EXIST));

        if (category.getStatus().equals("DELETED")) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_TEMPORARILY_FLAGGED);
        }
        return category;
    }

    public Category validateCategoryExistsById(int categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomNotFoundException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_DOES_NOT_EXIST));
    }

    public void checkExists(int id) {
        if (categoryRepository.checkExistsById(id).isEmpty()) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_DOES_NOT_EXIST);
        }
    }

    public void checkUsedProduct(int id) {
        if (productRepository.existsByCategoryId(id)) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_ID_USED_IN_PRODUCT);
        }
    }

    public void validateNameExists(String name) {
        if (categoryRepository.validateNameExists(name).isPresent()) {
            throw new BusinessCustomException(ErrorCategoryConstants.NAME_ERROR, ErrorCategoryConstants.CATEGORY_NAME_EXISTS);
        }
    }


}
