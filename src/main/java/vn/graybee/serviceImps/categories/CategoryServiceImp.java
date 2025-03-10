package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.categories.ConstantCategory;
import vn.graybee.enums.CategoryStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Category;
import vn.graybee.models.categories.CategoryManufacturer;
import vn.graybee.models.categories.CategorySubCategory;
import vn.graybee.projections.category.CategoryProjection;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;
import vn.graybee.repositories.categories.CategoryManufacturerRepository;
import vn.graybee.repositories.categories.CategoryRepository;
import vn.graybee.repositories.categories.CategorySubCategoryRepository;
import vn.graybee.repositories.categories.ManufacturerRepository;
import vn.graybee.repositories.categories.SubCategoryRepository;
import vn.graybee.requests.categories.CategoryCreateRequest;
import vn.graybee.requests.categories.CategoryUpdateRequest;
import vn.graybee.response.categories.CategoryResponse;
import vn.graybee.services.categories.CategoryService;
import vn.graybee.utils.TextUtils;
import vn.graybee.validation.CategoryValidation;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

    private final SubCategoryRepository subCategoryRepository;

    private final CategorySubCategoryRepository categorySubCategoryRepository;

    private final CategoryManufacturerRepository cmRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final CategoryRepository categoryRepository;

    private final CategoryValidation categoryValidation;

    public CategoryServiceImp(SubCategoryRepository subCategoryRepository, CategorySubCategoryRepository categorySubCategoryRepository, CategoryManufacturerRepository cmRepository, ManufacturerRepository manufacturerRepository, CategoryRepository categoryRepository, CategoryValidation categoryValidation) {
        this.subCategoryRepository = subCategoryRepository;
        this.categorySubCategoryRepository = categorySubCategoryRepository;
        this.cmRepository = cmRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.categoryRepository = categoryRepository;
        this.categoryValidation = categoryValidation;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BasicMessageResponse<CategoryResponse> createCategory(CategoryCreateRequest request) {
        categoryValidation.checkExistByName(request.getName());

        Category category = new Category(TextUtils.capitalize(request.getName()));
        category.setStatus(CategoryStatus.ACTIVE);
        category = categoryRepository.save(category);

        if (!request.getSubCategories().isEmpty()) {
            handleSubCategories(category.getId(), request.getSubCategories());
        }

        if (!request.getManufacturers().isEmpty()) {
            handleManufacturers(category.getId(), request.getManufacturers());
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

    private void handleSubCategories(int category, List<String> subCategoryNames) {
        List<Integer> subCategoryIds = subCategoryRepository.getIdByName(subCategoryNames);

        if (subCategoryIds.size() != subCategoryNames.size()) {
            throw new BusinessCustomException(
                    ConstantCategory.SUBCATEGORY_NAME,
                    ConstantCategory.SUBCATEGORY_DOES_NOT_EXIST
            );
        }

        List<CategorySubCategory> categorySubCategories = subCategoryIds.stream()
                .map(subId -> new CategorySubCategory(category, subId, CategoryStatus.PENDING))
                .collect(Collectors.toList());

        categorySubCategoryRepository.saveAll(categorySubCategories);
    }

    private void handleManufacturers(int category, List<String> manufacturerNames) {
        List<Integer> manufacturerIds = manufacturerRepository.getIdByName(manufacturerNames);

        if (manufacturerIds.size() != manufacturerNames.size()) {
            throw new BusinessCustomException(
                    ConstantCategory.MANUFACTURER_NAME,
                    ConstantCategory.MANUFACTURER_DOES_NOT_EXIST
            );
        }

        List<CategoryManufacturer> categoryManufacturers = manufacturerIds.stream()
                .map(manuId -> new CategoryManufacturer(category, manuId, CategoryStatus.PENDING))
                .collect(Collectors.toList());

        cmRepository.saveAll(categoryManufacturers);
    }

    @Override
    public BasicMessageResponse<CategoryResponse> findById(int id) {
        CategoryResponse category = categoryRepository.getById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_DOES_NOT_EXIST));

        return new BasicMessageResponse<>(200, "Tìm danh mục thành công", category);
    }

    @Override
    @Transactional
    public BasicMessageResponse<Integer> deleteCategoryById(int id) {

        categoryValidation.countProductById(id);

        categoryRepository.deleteByCategoryId(id);

        return new BasicMessageResponse<>(200, "Danh mục đã được xoá thành công!", id);
    }

    @Override
    @Transactional
    public BasicMessageResponse<CategoryResponse> updateCategory(int id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_DOES_NOT_EXIST));

        if (category.getProductCount() > 0) {
            throw new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_ID_USED_IN_PRODUCT);
        }
        category.setName(request.getName());
        category.setStatus(request.getStatus());

        categoryRepository.save(category);

        CategoryResponse categoryResponse = new CategoryResponse(
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getId(),
                category.getName(),
                category.getStatus(),
                category.getProductCount()
        );

        return new BasicMessageResponse<>(200, "Cập nhật danh mục thành công!", categoryResponse);
    }

    @Override
    @Transactional
    public BasicMessageResponse<List<CategoryProjection>> getCategories() {
        List<CategoryProjection> categories = categoryRepository.fetchAll();

        return new BasicMessageResponse<>(200, "", categories);
    }

    @Override
    public BasicMessageResponse<List<CategoryBasicInfoProjection>> getCategories_public() {
        List<CategoryBasicInfoProjection> categories = categoryRepository.findAllCategories_public();

        return new BasicMessageResponse<>(200, "", categories);
    }

}
