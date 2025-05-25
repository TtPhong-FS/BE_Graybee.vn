package vn.graybee.taxonomy.category.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.account.security.UserDetail;
import vn.graybee.common.constants.ConstantCategory;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantManufacturer;
import vn.graybee.common.constants.ConstantSubcategory;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.common.utils.TextUtils;
import vn.graybee.product.service.RedisProductService;
import vn.graybee.repositories.categories.CategoryManufacturerRepository;
import vn.graybee.repositories.categories.CategorySubCategoryRepository;
import vn.graybee.taxonomy.category.dto.request.CategoryCreateRequest;
import vn.graybee.taxonomy.category.dto.request.CategoryUpdateRequest;
import vn.graybee.taxonomy.category.dto.response.CategoryDto;
import vn.graybee.taxonomy.category.dto.response.CategoryIdManufacturerIdDto;
import vn.graybee.taxonomy.category.dto.response.CategoryIdSubcategoryIdDto;
import vn.graybee.taxonomy.category.dto.response.CategoryManufacturerBasicDto;
import vn.graybee.taxonomy.category.dto.response.CategoryProductCountDto;
import vn.graybee.taxonomy.category.dto.response.CategoryStatusDto;
import vn.graybee.taxonomy.category.dto.response.CategorySubcategoryBasicDto;
import vn.graybee.taxonomy.category.dto.response.SidebarDto;
import vn.graybee.taxonomy.category.model.Category;
import vn.graybee.taxonomy.category.repository.CategoryRepository;
import vn.graybee.taxonomy.category.service.CategoryClassificationService;
import vn.graybee.taxonomy.category.service.CategoryService;
import vn.graybee.taxonomy.dto.response.UpdateStatusDto;
import vn.graybee.taxonomy.enums.TaxonomyStatus;
import vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerBasicDto;
import vn.graybee.taxonomy.manufacturer.repository.ManufacturerRepository;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryBasicDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryTagsDto;
import vn.graybee.taxonomy.subcategory.repository.SubcategoryRepository;
import vn.graybee.taxonomy.subcategory.service.SubcategoryService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

    private static final String CACHE_KEY = "manufacturers:categoryId:";

    private static final EnumSet<TaxonomyStatus> ALLOWED_TRANSITIONS = EnumSet.of(
            TaxonomyStatus.ACTIVE,
            TaxonomyStatus.INACTIVE,
            TaxonomyStatus.DELETED,
            TaxonomyStatus.REMOVED
    );

    private final MessageSourceUtil messageSourceUtil;

    private final SubcategoryRepository subCategoryRepository;

    private final CategorySubCategoryRepository categorySubCategoryRepository;

    private final CategoryManufacturerRepository categoryManufacturerRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final CategoryRepository categoryRepository;

    private final SubcategoryService subCategoryService;

    private final RedisProductService redisProductService;

    private final CategoryClassificationService categoryClassificationService;

    public CategoryServiceImp(MessageSourceUtil messageSourceUtil, SubcategoryRepository subCategoryRepository, CategorySubCategoryRepository categorySubCategoryRepository, CategoryManufacturerRepository categoryManufacturerRepository, ManufacturerRepository manufacturerRepository, CategoryRepository categoryRepository, SubcategoryService subCategoryService, RedisProductService redisProductService, CategoryClassificationService categoryClassificationService) {
        this.messageSourceUtil = messageSourceUtil;
        this.subCategoryRepository = subCategoryRepository;
        this.categorySubCategoryRepository = categorySubCategoryRepository;
        this.categoryManufacturerRepository = categoryManufacturerRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryService = subCategoryService;
        this.redisProductService = redisProductService;
        this.categoryClassificationService = categoryClassificationService;
    }

    public List<SubcategoryBasicDto> validateSubcategoryNames(List<String> subcategoryNames) {
        if (subcategoryNames == null || subcategoryNames.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> requestedNames = subcategoryNames.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        List<SubcategoryBasicDto> subcategories = subCategoryRepository.findByNamesAndStatus(subcategoryNames, TaxonomyStatus.ACTIVE);

        Set<String> foundNames = subcategories.stream()
                .map(s -> s.getName().toLowerCase())
                .collect(Collectors.toSet());

        requestedNames.removeAll(foundNames);

        if (!requestedNames.isEmpty()) {
            throw new BusinessCustomException(
                    ConstantSubcategory.subcategories,
                    messageSourceUtil.get("subcategory.error.does_not_exists", new Object[]{String.join(", ", requestedNames)})
            );
        }
        return subcategories;
    }

    public List<ManufacturerBasicDto> validateManufacturerNames(List<String> manufacturerNames) {

        if (manufacturerNames == null || manufacturerNames.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> requestedNames = manufacturerNames.stream().map(String::toLowerCase).collect(Collectors.toSet());

        List<ManufacturerBasicDto> manufacturers = manufacturerRepository.findByNamesAndStatus(manufacturerNames, TaxonomyStatus.ACTIVE);
        Set<String> foundNames = manufacturers.stream()
                .map(m -> m.getName().toLowerCase())
                .collect(Collectors.toSet());

        requestedNames.removeAll(foundNames);

        if (!requestedNames.isEmpty()) {
            throw new BusinessCustomException(
                    ConstantManufacturer.manufacturers,
                    messageSourceUtil.get("manufacturer.error.does_not_exists", new Object[]{String.join(", ", requestedNames)})
            );
        }

        return manufacturers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BasicMessageResponse<CategoryDto> create(CategoryCreateRequest request) {

        if (categoryRepository.validateName(request.getName()).isPresent()) {
            throw new BusinessCustomException(ConstantCategory.name, messageSourceUtil.get("category.error.name_exists", new Object[]{request.getName()}));
        }

        List<SubcategoryBasicDto> subcategories = validateSubcategoryNames(request.getSubcategories());

        List<ManufacturerBasicDto> manufacturers = validateManufacturerNames(request.getManufacturers());

        Category category = new Category();
        category.setName(TextUtils.capitalize(request.getName()));
        category.setStatus(TaxonomyStatus.INACTIVE);
        category = categoryRepository.save(category);

        if (!subcategories.isEmpty() || !manufacturers.isEmpty()) {
            List<Integer> subcategoryIds = subcategories.stream().map(SubcategoryBasicDto::getId).toList();
            List<Integer> manufacturerIds = manufacturers.stream().map(ManufacturerBasicDto::getId).toList();

            categoryClassificationService.createCategoryClassification(category.getId(), subcategoryIds, manufacturerIds);

        }

        CategoryDto categoryDto = new CategoryDto(
                category
        );
        categoryDto.setSubcategories(subcategories);
        categoryDto.setManufacturers(manufacturers);

        return new BasicMessageResponse<>(201, messageSourceUtil.get("category.success_create", new Object[]{category.getName()}), categoryDto);
    }

    @Override
    public BasicMessageResponse<CategoryDto> findById(int id) {
        CategoryDto category = categoryRepository.getById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.error.does_not_exists")));

        List<SubcategoryBasicDto> subcategories = categorySubCategoryRepository.findByCategoryId(category.getId());
        List<ManufacturerBasicDto> manufacturers = categoryManufacturerRepository.findByCategoryId(category.getId());

        category.setManufacturers(manufacturers);
        category.setSubcategories(subcategories);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_find_by_id"), category);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {

        CategoryProductCountDto category = categoryRepository.getProductCountById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.error.does_not_exists")));

        if (category.getProductCount() > 0) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.error.in_use"));
        }

        categoryRepository.deleteById(id);

        redisProductService.deleteProductListPattern(category.getName());

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_delete", new Object[]{category.getName()}), category.getId());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<List<Integer>> deleteByIds(List<Integer> ids) {
        System.out.println(ids);
        return new BasicMessageResponse<>(200, ConstantCategory.success_delete_multiple, ids);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CategoryDto> update(int id, CategoryUpdateRequest request) {

        TaxonomyStatus status = TaxonomyStatus.getStatus(request.getStatus(), messageSourceUtil);

        List<SubcategoryBasicDto> subcategories = validateSubcategoryNames(request.getSubcategories());

        List<ManufacturerBasicDto> manufacturers = validateManufacturerNames(request.getManufacturers());

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.error.does_not_exists", new Object[]{request.getName()})));

        if (!category.getName().equals(request.getName()) && categoryRepository.existsByNameAndNotId(request.getName(), category.getId())) {
            throw new BusinessCustomException(ConstantCategory.name, messageSourceUtil.get("category.error.name_exists", new Object[]{request.getName()}));
        }

        if (category.getProductCount() == 0) {
            category.setName(request.getName());
        }

        category.setStatus(status);
        category.setUpdatedAt(LocalDateTime.now());

        category = categoryRepository.save(category);

        if (!subcategories.isEmpty() && !manufacturers.isEmpty()) {
            List<Integer> subcategoryIds = subcategories.stream().map(SubcategoryBasicDto::getId).toList();
            List<Integer> manufacturerIds = manufacturers.stream().map(ManufacturerBasicDto::getId).toList();

            categoryClassificationService.updateCategoryClassification(category.getId(), subcategoryIds, manufacturerIds);

        }

        CategoryDto categoryDto = new CategoryDto(
                category
        );
        categoryDto.setSubcategories(subcategories);
        categoryDto.setManufacturers(manufacturers);

//        redisProductService.deleteProductListPattern(category.getName());

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_update", new Object[]{category.getName()}), categoryDto);

    }

    @Override
    public BasicMessageResponse<List<CategoryDto>> fetchCategories_ADMIN() {
//
//        List<CategoryDto> categories = categoryRepository.fetchAll();
//
//        if (categories.isEmpty()) {
//            return new BasicMessageResponse<>(200, messageSourceUtil.get("category.empty_list"), categories);
//        }
//
//        List<Integer> categoryIds = categories.stream().map(CategoryDto::getId).toList();
//        List<CategoryManufacturerBasicDto> categoryManufacturer = categoryManufacturerRepository.findManufacturersByCategoryId_ADMIN(categoryIds);
//        List<CategorySubcategoryBasicDto> categorySubcategory = categorySubCategoryRepository.findSubcategoriesByCategoryId_ADMIN(categoryIds);
//
//        Map<Integer, List<ManufacturerBasicDto>> CateManuMap = categoryManufacturer.stream()
//                .collect(Collectors.groupingBy(CategoryManufacturerBasicDto::getCategoryId, Collectors.mapping(manufacturer -> new ManufacturerBasicDto(manufacturer.getManufacturerId(), manufacturer.getManufacturerName()), Collectors.toList())));
//
//        Map<Integer, List<SubcategoryBasicDto>> CateSubMap = categorySubcategory.stream()
//                .collect(Collectors.groupingBy(CategorySubcategoryBasicDto::getCategoryId, Collectors.mapping(subcategory -> new SubcategoryBasicDto(subcategory.getSubCategoryId(), subcategory.getSubcategoryName()), Collectors.toList())));
//
//        categories.forEach(category -> {
//            category.setManufacturers(CateManuMap.getOrDefault(category.getId(), Collections.emptyList()));
//            category.setSubcategories(CateSubMap.getOrDefault(category.getId(), Collections.emptyList()));
//        });
//
//        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_fetch_list"), categories);

        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CategoryIdManufacturerIdDto> deleteRelationByCategoryIdAndManufacturerId(int categoryId, int manufacturerId) {

        String categoryName = categoryRepository.getNameById(categoryId, TaxonomyStatus.ACTIVE)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.error.does_not_exists")));

        CategoryIdManufacturerIdDto response = categoryManufacturerRepository.findManufacturerIdWithCategoryId(categoryId, manufacturerId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.relation_manufacturer_not_linked", new Object[]{categoryName})));

        categoryManufacturerRepository.deleteManufacturerByIdAndCategoryById(categoryId, manufacturerId);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_remove_manufacturer_relation", new Object[]{categoryName}), response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CategoryIdSubcategoryIdDto> deleteRelationBySubcategoryByCategoryId(int categoryId, int subcategoryId) {

        String categoryName = categoryRepository.getNameById(categoryId, TaxonomyStatus.ACTIVE)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.error.does_not_exists")));

        CategoryIdSubcategoryIdDto response = categorySubCategoryRepository.findSubcategoryIdWithCategoryId(categoryId, subcategoryId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.relation_subcategory_not_linked", new Object[]{categoryName})));

        categorySubCategoryRepository.deleteSubcategoryByCategoryById(categoryId, subcategoryId);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_remove_subcategory_relation", new Object[]{categoryName}), response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<UpdateStatusDto> updateStatusById(int id, String status) {

        TaxonomyStatus newStatus = TaxonomyStatus.getStatus(status, messageSourceUtil);

        CategoryStatusDto category = categoryRepository.findNameAndStatusById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.error.does_not_exists")));

        if (category.getStatus() == newStatus) {
            return new BasicMessageResponse<>(200, messageSourceUtil.get("category.status_not_changed", new Object[]{category.getName()}), null);
        }

        if (category.getStatus() == TaxonomyStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.error.removed"));
        }

        if (category.getStatus() == TaxonomyStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.error.deleted"));
        }

        if (newStatus == TaxonomyStatus.PENDING
                && (category.getStatus() == TaxonomyStatus.ACTIVE || category.getStatus() == TaxonomyStatus.INACTIVE)) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.status.cannot_return"));
        }

        categoryRepository.updateStatusById(id, newStatus);

        UpdateStatusDto response = new UpdateStatusDto(category.getId(), newStatus, LocalDateTime.now());

        redisProductService.deleteProductListPattern(category.getName());

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_update_status", new Object[]{category.getName(), status}), response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CategoryDto> restoreById(int id, UserDetail userDetail) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.error.does_not_exists")));

        TaxonomyStatus currentStatus = category.getStatus();

        if (userDetail != null && !userDetail.getUser().isSuperAdmin() && currentStatus == TaxonomyStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("common.permission.super_admin_required"));
        }

        if (currentStatus != TaxonomyStatus.DELETED && currentStatus != TaxonomyStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.error.not_removed", new Object[]{category.getName()}));
        }

        categoryRepository.updateStatusById(id, TaxonomyStatus.INACTIVE);

        CategoryDto categoryDto = new CategoryDto(category);

        List<SubcategoryBasicDto> subcategories = categorySubCategoryRepository.findByCategoryId(category.getId());
        List<ManufacturerBasicDto> manufacturers = categoryManufacturerRepository.findByCategoryId(category.getId());

        categoryDto.setSubcategories(subcategories);
        categoryDto.setManufacturers(manufacturers);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_restore", new Object[]{category.getName()}), categoryDto);
    }

    @Override
    public BasicMessageResponse<List<SidebarDto>> getSidebar() {

        List<SidebarDto> sidebar = categoryRepository.getSidebar(TaxonomyStatus.ACTIVE);

        if (sidebar.isEmpty()) {
            return new BasicMessageResponse<>(200, messageSourceUtil.get("common.empty_list"), sidebar);
        }

        List<Integer> categoryIds = sidebar.stream().map(SidebarDto::getId).toList();

        List<CategoryManufacturerBasicDto> manuDtos = categoryManufacturerRepository.findManufacturersByCategoryId_ADMIN(categoryIds);
        List<CategorySubcategoryBasicDto> subDtos = categorySubCategoryRepository.findSubcategoriesByCategoryId_ADMIN(categoryIds);

        List<SubcategoryTagsDto> subcategories = subCategoryService.getForSidebar();

        Map<Integer, List<ManufacturerBasicDto>> CateManuMap = manuDtos.stream()
                .collect(Collectors.groupingBy(CategoryManufacturerBasicDto::getCategoryId, Collectors.mapping(manufacturer -> new ManufacturerBasicDto(manufacturer.getManufacturerId(), manufacturer.getManufacturerName()), Collectors.toList())));


        Map<Integer, SubcategoryTagsDto> subIdToDtoMap = subcategories.stream()
                .collect(Collectors.toMap(SubcategoryTagsDto::getId, Function.identity()));


        Map<Integer, List<SubcategoryTagsDto>> categoryToSubDtos = subDtos.stream()
                .collect(Collectors.groupingBy(
                        CategorySubcategoryBasicDto::getCategoryId,
                        Collectors.mapping(sub -> subIdToDtoMap.get(sub.getSubCategoryId()), Collectors.toList())
                ));


        sidebar.forEach(category -> {
            category.setManufacturers(CateManuMap.getOrDefault(category.getId(), Collections.emptyList()));
            category.setSubcategories(categoryToSubDtos.getOrDefault(category.getId(), Collections.emptyList()));

        });

        return new BasicMessageResponse<>(200, null, sidebar);
    }


}
