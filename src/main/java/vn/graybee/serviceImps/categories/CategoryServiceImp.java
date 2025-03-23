package vn.graybee.serviceImps.categories;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Category;
import vn.graybee.models.categories.CategoryManufacturer;
import vn.graybee.models.categories.CategorySubCategory;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;
import vn.graybee.repositories.categories.CategoryManufacturerRepository;
import vn.graybee.repositories.categories.CategoryRepository;
import vn.graybee.repositories.categories.CategorySubCategoryRepository;
import vn.graybee.repositories.categories.ManufacturerRepository;
import vn.graybee.repositories.categories.SubCategoryRepository;
import vn.graybee.requests.directories.CategoryCreateRequest;
import vn.graybee.requests.directories.CategoryUpdateRequest;
import vn.graybee.response.admin.directories.category.CategoryManuDto;
import vn.graybee.response.admin.directories.category.CategoryResponse;
import vn.graybee.response.admin.directories.category.CategorySubDto;
import vn.graybee.response.admin.directories.manufacturer.ManuDto;
import vn.graybee.response.admin.directories.subcate.SubcateDto;
import vn.graybee.services.categories.CategoryService;
import vn.graybee.utils.TextUtils;
import vn.graybee.validation.CategoryValidation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

    private static final String CACHE_KEY = "manufacturers:categoryId:";

    private final SubCategoryRepository subCategoryRepository;

    private final CategorySubCategoryRepository categorySubCategoryRepository;

    private final CategoryManufacturerRepository cmRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final CategoryRepository categoryRepository;

    private final CategoryValidation categoryValidation;

    private final StringRedisTemplate redisTemplate;


    public CategoryServiceImp(SubCategoryRepository subCategoryRepository, CategorySubCategoryRepository categorySubCategoryRepository, CategoryManufacturerRepository cmRepository, ManufacturerRepository manufacturerRepository, CategoryRepository categoryRepository, CategoryValidation categoryValidation, StringRedisTemplate redisTemplate) {
        this.subCategoryRepository = subCategoryRepository;
        this.categorySubCategoryRepository = categorySubCategoryRepository;
        this.cmRepository = cmRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.categoryRepository = categoryRepository;
        this.categoryValidation = categoryValidation;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BasicMessageResponse<CategoryResponse> createCategory(CategoryCreateRequest request) {
        categoryValidation.checkExistByName(request.getCategoryName());

        Category category = new Category(TextUtils.capitalize(request.getCategoryName()));
        category.setStatus("ACTIVE");
        Category savedCategory = categoryRepository.save(category);

        List<SubcateDto> subDto = new ArrayList<>();
        if (!request.getSubcategories().isEmpty()) {
            subDto = handleSubCategories(savedCategory.getId(), request.getSubcategories());
        }

        List<ManuDto> manuDtos = new ArrayList<>();
        if (!request.getManufacturers().isEmpty()) {
            manuDtos = handleManufacturers(savedCategory.getId(), request.getManufacturers());
        }

        CategoryResponse categoryResponse = new CategoryResponse(
                savedCategory,
                subDto,
                manuDtos
        );

        return new BasicMessageResponse<>(201, "Danh mục đã được tạo thành công!", categoryResponse);
    }

    private List<SubcateDto> handleSubCategories(int category, List<Integer> subCateIds) {
        List<SubcateDto> subCateDtos = subCategoryRepository.findByIds(subCateIds);

        if (subCateDtos.size() != subCateIds.size()) {
            throw new BusinessCustomException(
                    ConstantCategory.SUBCATEGORY_NAME,
                    ConstantCategory.SUBCATEGORY_DOES_NOT_EXIST
            );
        }

        List<CategorySubCategory> categorySubCategories = subCateDtos.stream()
                .map(sub -> new CategorySubCategory(category, sub.getId()))
                .collect(Collectors.toList());

        categorySubCategoryRepository.saveAll(categorySubCategories);

        return subCateDtos;
    }

    private List<ManuDto> handleManufacturers(int category, List<Integer> manuIds) {
        List<ManuDto> manufacturers = manufacturerRepository.findByIds(manuIds);

        if (manufacturers.size() != manuIds.size()) {
            throw new BusinessCustomException(ConstantCategory.MANUFACTURER_NAME, ConstantCategory.MANUFACTURER_DOES_NOT_EXIST);
        }

        List<CategoryManufacturer> categoryManufacturers = manufacturers.stream()
                .map(manu -> new CategoryManufacturer(category, manu.getId()))
                .collect(Collectors.toList());

//        Set<String> keys = redisTemplate.keys(CACHE_KEY + "*");
//        if (!keys.isEmpty()) {
//            redisTemplate.delete(keys);
//        }

        cmRepository.saveAll(categoryManufacturers);

        return manufacturers;
    }

    @Override
    public BasicMessageResponse<CategoryResponse> findById(int id) {
        CategoryResponse category = categoryRepository.getById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_DOES_NOT_EXIST));

        List<SubcateDto> subcateDtos = categorySubCategoryRepository.findByCategoryId(category.getId());
        List<ManuDto> manuDtos = cmRepository.findByCategoryId(category.getId());

        category.setManufacturers(manuDtos);
        category.setSubcategories(subcateDtos);

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
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CategoryResponse> updateCategory(int id, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_DOES_NOT_EXIST));

        if (category.getProductCount() > 0) {
            throw new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.CATEGORY_ID_USED_IN_PRODUCT);
        }

        category.setCategoryName(request.getCategoryName());
        if (request.getStatus() != null) {
            category.setStatus(request.getStatus().name());
        } else {
            category.setStatus("PENDING");
        }

        List<SubcateDto> subCategoryResponse = new ArrayList<>();
        List<ManuDto> manufacturerResponse = new ArrayList<>();

        if (!request.getSubcategories().isEmpty()) {
            List<Integer> newSubcategoryIds = request.getSubcategories();
            List<Integer> validateSubIds = subCategoryRepository.findAllByIds(newSubcategoryIds);

            if (validateSubIds.size() != newSubcategoryIds.size()) {
                throw new BusinessCustomException(ConstantCategory.SUBCATEGORY_NAME, ConstantCategory.SUBCATEGORY_DOES_NOT_EXIST);
            }

            List<Integer> currentSubcategoryIds = categorySubCategoryRepository.findSubcategoryIdsByCategoryId(category.getId());

            Set<Integer> newSet = new HashSet<>(newSubcategoryIds);

            categorySubCategoryRepository.deleteByCategoryIdAndSubCategoryIdNotIn(category.getId(), new ArrayList<>(newSet));

            List<CategorySubCategory> newRelations = newSet.stream()
                    .filter(subId -> !currentSubcategoryIds.contains(subId))
                    .map(sub -> new CategorySubCategory(category.getId(), sub))
                    .toList();

            categorySubCategoryRepository.saveAll(newRelations);

            subCategoryResponse = subCategoryRepository.findByIds(newSubcategoryIds);
        }


        if (!request.getManufacturers().isEmpty()) {

            List<Integer> newManufacturerIds = request.getManufacturers();
            List<Integer> validateManuIds = subCategoryRepository.findAllByIds(newManufacturerIds);

            if (validateManuIds.size() != newManufacturerIds.size()) {
                throw new BusinessCustomException(ConstantCategory.MANUFACTURER_NAME, ConstantCategory.MANUFACTURER_DOES_NOT_EXIST);
            }

            List<Integer> currentManufacturerIds = cmRepository.findManufacturerIdsByCategoryId(category.getId());

            Set<Integer> newSet = new HashSet<>(newManufacturerIds);

            cmRepository.deleteByCategoryIdAndManufacturerIdNotIn(category.getId(), new ArrayList<>(newSet));

            List<CategoryManufacturer> newRelations = newSet.stream()
                    .filter(manuid -> !currentManufacturerIds.contains(manuid))
                    .map(manu -> new CategoryManufacturer(category.getId(), manu)).toList();

            cmRepository.saveAll(newRelations);

            manufacturerResponse = manufacturerRepository.findByIds(newManufacturerIds);

        }

        Category savedCategory = categoryRepository.save(category);


        CategoryResponse categoryResponse = new CategoryResponse(
                savedCategory,
                subCategoryResponse,
                manufacturerResponse
        );

        return new BasicMessageResponse<>(200, "Cập nhật danh mục thành công!", categoryResponse);

    }

    @Override
    @Transactional
    public BasicMessageResponse<List<CategoryResponse>> fetchCategories_ADMIN() {

        List<CategoryResponse> categories = categoryRepository.fetchAll_ADMIN();

        List<Integer> categoryIds = categories.stream().map(CategoryResponse::getId).toList();

        List<CategoryManuDto> manuDtos = cmRepository.findManufacturersByCategoryId_ADMIN(categoryIds);

        List<CategorySubDto> subDtos = categorySubCategoryRepository.findSubcategoriesByCategoryId_ADMIN(categoryIds);


        Map<Integer, List<ManuDto>> CateManuMap = new HashMap<>();
        Map<Integer, List<SubcateDto>> CateSubMap = new HashMap<>();

        if (!manuDtos.isEmpty()) {
            CateManuMap = manuDtos
                    .stream()
                    .collect(Collectors.groupingBy(CategoryManuDto::getCategoryId,
                            Collectors.mapping(
                                    manu -> new ManuDto(manu.getManufacturerId(), manu.getManufacturerName()), Collectors.toList())));
        }

        if (!subDtos.isEmpty()) {
            CateSubMap = subDtos
                    .stream()
                    .collect(Collectors.groupingBy(CategorySubDto::getCategoryId,
                            Collectors.mapping(
                                    cate -> new SubcateDto(cate.getSubCategoryId(), cate.getSubcategoryName()), Collectors.toList())));
        }


        for (CategoryResponse category : categories) {
            if (!CateSubMap.isEmpty()) {
                List<SubcateDto> subs = CateSubMap.getOrDefault(category.getId(), new ArrayList<>());
                category.setSubcategories(subs);
            } else {
                category.setSubcategories(Collections.emptyList());
            }
            if (!CateManuMap.isEmpty()) {
                List<ManuDto> manus = CateManuMap.getOrDefault(category.getId(), new ArrayList<>());
                category.setManufacturers(manus);
            } else {
                category.setManufacturers(Collections.emptyList());
            }
        }

        return new BasicMessageResponse<>(200, "Lấy danh sách danh mục thành công!", categories);
    }

    @Override
    public BasicMessageResponse<List<CategoryBasicInfoProjection>> getCategories_public() {
        List<CategoryBasicInfoProjection> categories = categoryRepository.findAllCategories_public();

        return new BasicMessageResponse<>(200, "", categories);
    }

}
