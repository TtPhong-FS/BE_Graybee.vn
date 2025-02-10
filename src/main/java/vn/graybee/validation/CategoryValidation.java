package vn.graybee.validation;

import org.springframework.stereotype.Service;
import vn.graybee.constants.categories.ErrorCategoryConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.models.business.Category;
import vn.graybee.repositories.business.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryValidation {

    private final CategoryRepository categoryRepository;

    public CategoryValidation(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findToCreateProduct(String categoryName) {
        Optional<Category> category = categoryRepository.findCategoryByCategoryName(categoryName);
        if (category.isEmpty()) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_DOES_NOT_EXIST);
        }
        if (category.get().isDeleted()) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_TEMPORARILY_FLAGGED);
        }
        return category.get();
    }

    public Category findToUpdateStatusDelete(long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_DOES_NOT_EXIST);
        }
        return category.get();
    }

    public void checkCategoryNameExists(String categoryName) {
        if (categoryRepository.checkCategoryNameExists(categoryName).isPresent()) {
            throw new BusinessCustomException(ErrorCategoryConstants.NAME_ERROR, ErrorCategoryConstants.CATEGORY_NAME_EXISTS);
        }
    }

    public void checkExists(long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_DOES_NOT_EXIST);
        }
    }

    public void checkProductExists(long categoryId) {
        if (categoryRepository.checkProductIdExists(categoryId).isPresent()) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_ID_USED_IN_PRODUCT);
        }
    }

}
