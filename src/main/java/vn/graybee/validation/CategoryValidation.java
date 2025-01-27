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

    public Category ensureCategoryBeforeCreateProduct(long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.get().getIsDelete().equals("true")) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_TEMPORARILY_FLAGGED);
        }
        if (category.isEmpty()) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_DOES_NOT_EXIST);
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

    public void ensureCategoryNameBeforeCreate(String categoryName) {
        if (categoryRepository.ensureCategoryNameBeforeCreate(categoryName).isPresent()) {
            throw new BusinessCustomException(ErrorCategoryConstants.NAME_ERROR, ErrorCategoryConstants.CATEGORY_NAME_EXISTS);
        }
    }

    public void ensureExistsById(long id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_DOES_NOT_EXIST);
        }
    }

    public void checkProductExistsByCategoryId(long categoryId) {
        if (categoryRepository.checkProductIdExistsByCategoryId(categoryId).isPresent()) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_ID_USED_IN_PRODUCT);
        }
    }

}
