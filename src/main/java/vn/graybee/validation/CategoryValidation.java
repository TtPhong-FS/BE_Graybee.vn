package vn.graybee.validation;

import org.springframework.stereotype.Service;
import vn.graybee.constants.categories.ErrorCategoryConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.models.categories.Category;
import vn.graybee.repositories.categories.CategoryRepository;

@Service
public class CategoryValidation {

    private final CategoryRepository categoryRepository;

    public CategoryValidation(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category validateCategoryExistsByName(String categoryName) {
        Category category = categoryRepository.findToCreateProduct(categoryName)
                .orElseThrow(() -> new CustomNotFoundException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_DOES_NOT_EXIST));

        if (category.isDeleted()) {
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

    public void validateNameExists(String name) {
        if (categoryRepository.validateNameExists(name).isPresent()) {
            throw new BusinessCustomException(ErrorCategoryConstants.NAME_ERROR, ErrorCategoryConstants.CATEGORY_NAME_EXISTS);
        }
    }


}
