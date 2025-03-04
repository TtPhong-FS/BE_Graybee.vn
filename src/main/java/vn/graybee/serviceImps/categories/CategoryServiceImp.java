package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.categories.ErrorCategoryConstants;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Category;
import vn.graybee.models.categories.CategorySubCategory;
import vn.graybee.models.categories.SubCategory;
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
        category.setStatus("ACTIVE");

        Category savedCategory = categoryRepository.save(category);

        CategoryResponse categoryResponse = new CategoryResponse(
                savedCategory.getCreatedAt(),
                savedCategory.getUpdatedAt(),
                savedCategory.getId(),
                savedCategory.getName(),
                savedCategory.getStatus(),
                savedCategory.getProductCount()
        );

        return new BasicMessageResponse<>(201, "Danh mục đã được tạo thành công! Kiểm tra dữ liệu dưới bảng", categoryResponse);
    }

    @Override
    @Transactional
    public BasicMessageResponse<CategoryResponse> createCategoryWithSubCategory(CategoryCreateRequest request) {
        categoryValidation.validateNameExists(request.getName());

        Category category = new Category(request.getName());
        category.setStatus("ACTIVE");
        category = categoryRepository.save(category);

        if (!request.getSubCategories().isEmpty()) {
            Category finalCategory = category;
            request.getSubCategories().forEach(subName -> {
                SubCategory subCategory = subCategoryRepository.findByName(subName)
                        .orElseThrow(() -> new BusinessCustomException(
                                ErrorCategoryConstants.GENERAL_ERROR,
                                ErrorCategoryConstants.SUBCATEGORY_DOES_NOT_EXIST
                        ));

                // Tạo quan hệ CategorySubCategory
                CategorySubCategory categorySubCategory = new CategorySubCategory();
                categorySubCategory.setCategory(finalCategory);
                categorySubCategory.setSubCategory(subCategory);

                // Lưu quan hệ vào DB
                categorySubCategoryRepository.save(categorySubCategory);
            });
        }

        CategoryResponse categoryResponse = new CategoryResponse(
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getId(),
                category.getName(),
                category.getStatus(),
                category.getProductCount()
        );

        return new BasicMessageResponse<>(201, "Danh mục đã được tạo thành công! Kiểm tra dữ liệu dưới bảng", categoryResponse);
    }

    @Override
    public BasicMessageResponse<Category> getCategoryById(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CustomNotFoundException(ErrorCategoryConstants.GENERAL_ERROR, ErrorCategoryConstants.CATEGORY_DOES_NOT_EXIST));

        return new BasicMessageResponse<>(200, "Tìm danh mục thành công", category);
    }

    @Override
    @Transactional
    public BasicMessageResponse<Integer> deleteCategoryById(int id) {

        categoryValidation.checkExists(id);
        categoryValidation.checkUsedProduct(id);
        categoryRepository.deleteByCategoryId(id);

        return new BasicMessageResponse<>(200, "Danh mục đã được xoá thành công!", id);
    }

    @Override
    @Transactional
    public BasicMessageResponse<CategoryStatusResponse> updateStatusDeleteRecord(int id) {
        Category category = categoryValidation.validateCategoryExistsById(id);


        categoryRepository.save(category);

        CategoryStatusResponse categoryResponse = new CategoryStatusResponse(
                id,
                false
        );

        return new BasicMessageResponse<>(200, "Cập nhật danh mục thành công!", categoryResponse);
    }

    @Override
    @Transactional
    public BasicMessageResponse<List<CategoryProjection>> getCategories() {
        List<CategoryProjection> categories = categoryRepository.fetchCategories();

        return new BasicMessageResponse<>(200, "", categories);
    }

    @Override
    public BasicMessageResponse<List<CategoryBasicInfoProjection>> getCategories_public() {
        List<CategoryBasicInfoProjection> categories = categoryRepository.findAllCategories_public();

        return new BasicMessageResponse<>(200, "", categories);
    }

}
