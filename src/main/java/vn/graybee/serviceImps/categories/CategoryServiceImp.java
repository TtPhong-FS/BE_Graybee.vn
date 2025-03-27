package vn.graybee.serviceImps.categories;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantManufacturer;
import vn.graybee.constants.ConstantSubcategory;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.directories.Category;
import vn.graybee.models.directories.CategoryManufacturer;
import vn.graybee.models.directories.CategorySubCategory;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;
import vn.graybee.repositories.categories.CategoryManufacturerRepository;
import vn.graybee.repositories.categories.CategoryRepository;
import vn.graybee.repositories.categories.CategorySubCategoryRepository;
import vn.graybee.repositories.categories.ManufacturerRepository;
import vn.graybee.repositories.categories.SubCategoryRepository;
import vn.graybee.requests.directories.CategoryCreateRequest;
import vn.graybee.requests.directories.CategoryUpdateRequest;
import vn.graybee.response.admin.directories.category.CategoryManuDto;
import vn.graybee.response.admin.directories.category.CategoryManufacturerIdResponse;
import vn.graybee.response.admin.directories.category.CategoryProductCountResponse;
import vn.graybee.response.admin.directories.category.CategoryResponse;
import vn.graybee.response.admin.directories.category.CategorySubDto;
import vn.graybee.response.admin.directories.category.CategorySubcategoryIdResponse;
import vn.graybee.response.admin.directories.manufacturer.ManuDto;
import vn.graybee.response.admin.directories.subcate.SubcateDto;
import vn.graybee.services.categories.CategoryService;
import vn.graybee.utils.TextUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

    private final StringRedisTemplate redisTemplate;

    public CategoryServiceImp(SubCategoryRepository subCategoryRepository, CategorySubCategoryRepository categorySubCategoryRepository, CategoryManufacturerRepository cmRepository, ManufacturerRepository manufacturerRepository, CategoryRepository categoryRepository, StringRedisTemplate redisTemplate) {
        this.subCategoryRepository = subCategoryRepository;
        this.categorySubCategoryRepository = categorySubCategoryRepository;
        this.cmRepository = cmRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.categoryRepository = categoryRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BasicMessageResponse<CategoryResponse> create(CategoryCreateRequest request) {

        if (categoryRepository.validateName(request.getName()).isPresent()) {
            throw new BusinessCustomException(ConstantCategory.name, ConstantCategory.name_exists);
        }


        List<SubcateDto> subcategories = subCategoryRepository.findByIds(request.getSubcategories());
        Set<Integer> foundSubcategoryIds = subcategories.stream()
                .map(SubcateDto::getId)
                .collect(Collectors.toSet());

        if (!foundSubcategoryIds.containsAll(request.getSubcategories())) {
            throw new BusinessCustomException(ConstantSubcategory.subcategories, ConstantSubcategory.does_not_exists);
        }

        List<ManuDto> manufacturers = manufacturerRepository.findByIds(request.getManufacturers());
        Set<Integer> foundManufacturerIds = manufacturers.stream()
                .map(ManuDto::getId)
                .collect(Collectors.toSet());

        if (!foundManufacturerIds.containsAll(request.getManufacturers())) {
            throw new BusinessCustomException(ConstantManufacturer.manufacturers, ConstantManufacturer.does_not_exists);
        }


        Category category = new Category();
        category.setName(TextUtils.capitalize(request.getName()));
        category.setStatus("ACTIVE");
        category = categoryRepository.save(category);

        if (request.getSubcategories() != null && !request.getSubcategories().isEmpty()) {
            handleSubCategories(category.getId(), foundSubcategoryIds);
        }

        if (request.getManufacturers() != null && !request.getManufacturers().isEmpty()) {
            handleManufacturers(category.getId(), foundManufacturerIds);
        }

        CategoryResponse categoryResponse = new CategoryResponse(
                category,
                subcategories,
                manufacturers
        );

        return new BasicMessageResponse<>(201, ConstantCategory.success_create, categoryResponse);
    }

    private void handleSubCategories(int category, Set<Integer> subCateIds) {

        List<CategorySubCategory> categorySubCategories = subCateIds.stream()
                .map(sub -> new CategorySubCategory(category, sub))
                .collect(Collectors.toList());

        if (!categorySubCategories.isEmpty()) {
            categorySubCategoryRepository.saveAll(categorySubCategories);
        }

    }

    private void handleManufacturers(int category, Set<Integer> manuIds) {

        List<CategoryManufacturer> categoryManufacturers = manuIds.stream()
                .map(manu -> new CategoryManufacturer(category, manu))
                .collect(Collectors.toList());

        if (!categoryManufacturers.isEmpty()) {
            cmRepository.saveAll(categoryManufacturers);

        }

    }

    @Override
    public BasicMessageResponse<CategoryResponse> findById(int id) {
        CategoryResponse category = categoryRepository.getById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, ConstantCategory.does_not_exists));

        List<SubcateDto> subcateDtos = categorySubCategoryRepository.findByCategoryId(category.getId());
        List<ManuDto> manuDtos = cmRepository.findByCategoryId(category.getId());

        category.setManufacturers(manuDtos);
        category.setSubcategories(subcateDtos);

        return new BasicMessageResponse<>(200, ConstantCategory.success_find_by_id, category);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {

        CategoryProductCountResponse category = categoryRepository.checkExistsAndGetProductCountById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCategory.does_not_exists));

        if (category.getProductCount() > 0) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantCategory.products_in_use);
        }

        categoryRepository.deleteById(id);

        return new BasicMessageResponse<>(200, ConstantCategory.success_delete, category.getId());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CategoryResponse> update(int id, CategoryUpdateRequest request) {

        List<SubcateDto> subcategories = subCategoryRepository.findByIds(request.getSubcategories());
        Set<Integer> foundSubcategoryIds = subcategories.stream()
                .map(SubcateDto::getId)
                .collect(Collectors.toSet());

        if (!foundSubcategoryIds.containsAll(request.getSubcategories())) {
            throw new BusinessCustomException(ConstantSubcategory.subcategories, ConstantSubcategory.does_not_exists);
        }

        List<ManuDto> manufacturers = manufacturerRepository.findByIds(request.getManufacturers());
        Set<Integer> foundManufacturerIds = manufacturers.stream()
                .map(ManuDto::getId)
                .collect(Collectors.toSet());

        if (!foundManufacturerIds.containsAll(request.getManufacturers())) {
            throw new BusinessCustomException(ConstantManufacturer.manufacturers, ConstantManufacturer.does_not_exists);
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, ConstantCategory.does_not_exists));

        if (!category.getName().equals(request.getName()) && categoryRepository.existsByNameAndNotId(request.getName(), category.getId())) {
            throw new BusinessCustomException(ConstantCategory.name, ConstantCategory.name_exists);
        }

        if (category.getProductCount() == 0) {
            category.setName(request.getName());
        }

        if (request.getStatus() != null) {
            category.setStatus(request.getStatus().name());
        } else {
            category.setStatus("PENDING");
        }

        category.setUpdatedAt(LocalDateTime.now());

        category = categoryRepository.save(category);
        int categoryId = category.getId();

        if (request.getSubcategories() != null && !request.getSubcategories().isEmpty()) {
            List<Integer> currentSubcategoryIds = categorySubCategoryRepository.findSubcategoryIdsByCategoryId(categoryId);

            Set<Integer> newSet = new HashSet<>(request.getSubcategories());

            categorySubCategoryRepository.deleteByCategoryIdAndSubCategoryIdNotIn(categoryId, new ArrayList<>(newSet));

            List<CategorySubCategory> newRelations = newSet.stream()
                    .filter(subId -> !currentSubcategoryIds.contains(subId))
                    .map(sub -> new CategorySubCategory(categoryId, sub))
                    .toList();

            if (!newRelations.isEmpty()) {
                categorySubCategoryRepository.saveAll(newRelations);
            }
        }

        if (request.getManufacturers() != null && !request.getManufacturers().isEmpty()) {

            List<Integer> currentManufacturerIds = cmRepository.findManufacturerIdsByCategoryId(categoryId);

            Set<Integer> newSet = new HashSet<>(request.getManufacturers());

            cmRepository.deleteByCategoryIdAndManufacturerIdNotIn(categoryId, new ArrayList<>(newSet));

            List<CategoryManufacturer> newRelations = newSet.stream()
                    .filter(manuid -> !currentManufacturerIds.contains(manuid))
                    .map(manu -> new CategoryManufacturer(categoryId, manu)).toList();

            if (!newRelations.isEmpty()) {
                cmRepository.saveAll(newRelations);
            }
        }

        CategoryResponse categoryResponse = new CategoryResponse(
                category,
                subcategories,
                manufacturers
        );

        return new BasicMessageResponse<>(200, ConstantCategory.success_update, categoryResponse);

    }

    @Override
    @Transactional
    public BasicMessageResponse<List<CategoryResponse>> fetchCategories_ADMIN() {

        List<CategoryResponse> categories = categoryRepository.fetchAll_ADMIN();

        if (categories.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantGeneral.empty_list, categories);
        }

        List<Integer> categoryIds = categories.stream().map(CategoryResponse::getId).toList();
        List<CategoryManuDto> manuDtos = cmRepository.findManufacturersByCategoryId_ADMIN(categoryIds);
        List<CategorySubDto> subDtos = categorySubCategoryRepository.findSubcategoriesByCategoryId_ADMIN(categoryIds);

        Map<Integer, List<ManuDto>> CateManuMap = manuDtos.stream()
                .collect(Collectors.groupingBy(CategoryManuDto::getCategoryId, Collectors.mapping(manufacturer -> new ManuDto(manufacturer.getManufacturerId(), manufacturer.getManufacturerName()), Collectors.toList())));

        Map<Integer, List<SubcateDto>> CateSubMap = subDtos.stream()
                .collect(Collectors.groupingBy(CategorySubDto::getCategoryId, Collectors.mapping(subcategory -> new SubcateDto(subcategory.getSubCategoryId(), subcategory.getSubcategoryName()), Collectors.toList())));

        categories.forEach(category -> {
            category.setManufacturers(CateManuMap.getOrDefault(category.getId(), Collections.emptyList()));
            category.setSubcategories(CateSubMap.getOrDefault(category.getId(), Collections.emptyList()));
        });

        return new BasicMessageResponse<>(200, ConstantCategory.success_fetch_categories, categories);
    }

    @Override
    public BasicMessageResponse<CategoryManufacturerIdResponse> deleteRelationByCategoryIdAndManufacturerId(int categoryId, int manufacturerId) {

        CategoryManufacturerIdResponse response = cmRepository.findManufacturerIdWithCategoryId(categoryId, manufacturerId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCategory.manufacturer_relation_does_not_exists));

        cmRepository.deleteManufacturerByIdAndCategoryById(categoryId, manufacturerId);

        return new BasicMessageResponse<>(200, ConstantCategory.success_delete_manufacturer_relation, response);
    }

    @Override
    public BasicMessageResponse<CategorySubcategoryIdResponse> deleteRelationBySubcategoryByCategoryId(int categoryId, int subcategoryId) {

        CategorySubcategoryIdResponse response = categorySubCategoryRepository.findSubcategoryIdWithCategoryId(categoryId, subcategoryId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCategory.subcategory_relation_does_not_exists));

        categorySubCategoryRepository.deleteSubcategoryByCategoryById(categoryId, subcategoryId);

        return new BasicMessageResponse<>(200, ConstantCategory.success_delete_subcategory_relation, response);
    }

    @Override
    public BasicMessageResponse<List<CategoryBasicInfoProjection>> getCategories_public() {
        List<CategoryBasicInfoProjection> categories = categoryRepository.findAllCategories_public();

        return new BasicMessageResponse<>(200, "", categories);
    }

}
