package vn.graybee.serviceImps.categories;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.categories.ErrorCategoryConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Category;
import vn.graybee.projections.CategoryProjection;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;
import vn.graybee.repositories.categories.CategoryRepository;
import vn.graybee.repositories.categories.CategorySubCategoryRepository;
import vn.graybee.repositories.categories.SubCategoryRepository;
import vn.graybee.requests.categories.CategoryCreateRequest;
import vn.graybee.response.categories.CategoryResponse;
import vn.graybee.response.categories.CategoryStatusResponse;
import vn.graybee.services.categories.CategoryService;
import vn.graybee.utils.TextUtils;
import vn.graybee.validation.CategoryValidation;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    private final SubCategoryRepository subCategoryRepository;

    private final CategorySubCategoryRepository categorySubCategoryRepository;

    private final CategoryRepository categoryRepository;

    private final CategoryValidation categoryValidation;

    public CategoryServiceImp(SubCategoryRepository subCategoryRepository, CategorySubCategoryRepository categorySubCategoryRepository, CategoryRepository categoryRepository, CategoryValidation categoryValidation) {
        this.subCategoryRepository = subCategoryRepository;
        this.categorySubCategoryRepository = categorySubCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.categoryValidation = categoryValidation;
    }

    @Override
    public BasicMessageResponse<CategoryResponse> createCategory(CategoryCreateRequest request) {
        categoryValidation.validateNameExists(request.getName());

        Category category = new Category(
                TextUtils.capitalizeEachWord(request.getName())
        );
        category.setDeleted(false);

        Category savedCategory = categoryRepository.save(category);

        CategoryResponse categoryResponse = new CategoryResponse(
                savedCategory.getCreatedAt(),
                savedCategory.getUpdatedAt(),
                savedCategory.getId(),
                savedCategory.getName(),
                savedCategory.isDeleted());

        return new BasicMessageResponse<>(201, "Create Category success. Response after create", categoryResponse);
    }

    @Override
    public CategoryResponse createCategoryWithSubCategory(CategoryCreateRequest request) {
        return null;
    }

    @Override
    public BasicMessageResponse<Integer> deleteCategoryById(int id) {
        try {
            categoryValidation.checkExists(id);
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessCustomException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_ID_USED_IN_PRODUCT);
        }
        return new BasicMessageResponse<>(200, "Deleted category success with id " + id, id);
    }

    @Override
    @Transactional
    public BasicMessageResponse<CategoryStatusResponse> updateStatusDeleteRecord(int id) {
        Category category = categoryValidation.validateCategoryExistsById(id);

        category.setDeleted(!category.isDeleted());

        categoryRepository.save(category);

        CategoryStatusResponse categoryResponse = new CategoryStatusResponse(
                id,
                category.isDeleted()
        );

        return new BasicMessageResponse<>(200, "Update status category success", categoryResponse);
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CustomNotFoundException(ErrorCategoryConstants.CATEGORY, ErrorCategoryConstants.CATEGORY_DOES_NOT_EXIST));
    }

    @Override
    public BasicMessageResponse<List<CategoryProjection>> getCategories() {
        List<CategoryProjection> categories = categoryRepository.fetchCategories();

        return new BasicMessageResponse<>(200, "List categories: ", categories);
    }

    @Override
    public BasicMessageResponse<List<CategoryBasicInfoProjection>> getCategories_public() {
        List<CategoryBasicInfoProjection> categories = categoryRepository.findAllCategories_public();

        return new BasicMessageResponse<>(200, "Public: This is list categories: ", categories);
    }

}
