package vn.graybee.validation;

import org.springframework.stereotype.Service;
import vn.graybee.constants.categories.ConstantCategory;
import vn.graybee.enums.CategoryStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.repositories.categories.CategoryRepository;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.response.categories.CategoryResponse;
import vn.graybee.response.categories.CategoryStatusResponse;

@Service
public class CategoryValidation {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    public CategoryValidation(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public int getIdByName(String categoryName) {
        CategoryStatusResponse category = categoryRepository.getStatusAndIdByName(categoryName)
                .orElseThrow(() -> new CustomNotFoundException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_DOES_NOT_EXIST));

        if (category.getStatus().equals(CategoryStatus.DELETED)) {
            throw new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_TEMPORARILY_FLAGGED);
        }
        return category.getId();
    }

    public CategoryResponse validateCategoryExistsById(int categoryId) {
        return categoryRepository.getById(categoryId)
                .orElseThrow(() -> new CustomNotFoundException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_DOES_NOT_EXIST));
    }

    public void countProductById(int id) {
        int products = categoryRepository.getCountProductById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_DOES_NOT_EXIST));
        if (products > 0) {
            throw new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_ID_USED_IN_PRODUCT);
        }
    }

    public void findById(int id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_DOES_NOT_EXIST);
        }
    }

    public void checkExistByName(String name) {
        if (categoryRepository.validateNameExists(name).isPresent()) {
            throw new BusinessCustomException(ConstantCategory.NAME, ConstantCategory.CATEGORY_NAME_EXISTS);
        }
    }

}
