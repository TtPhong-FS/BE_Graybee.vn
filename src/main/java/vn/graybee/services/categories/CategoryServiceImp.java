package vn.graybee.services.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantManufacturer;
import vn.graybee.constants.ConstantSubcategory;
import vn.graybee.enums.DirectoryStatus;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.directories.Category;
import vn.graybee.models.users.UserPrincipal;
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
import vn.graybee.response.admin.directories.category.CategoryStatusDto;
import vn.graybee.response.admin.directories.category.CategorySubDto;
import vn.graybee.response.admin.directories.category.CategorySubcategoryIdResponse;
import vn.graybee.response.admin.directories.general.UpdateStatusResponse;
import vn.graybee.response.admin.directories.manufacturer.ManufacturerDto;
import vn.graybee.response.admin.directories.subcategory.SubcategoryDto;
import vn.graybee.response.publics.sidebar.SidebarDto;
import vn.graybee.services.classifications.ICategoryClassificationService;
import vn.graybee.services.products.RedisProductService;
import vn.graybee.utils.MessageSourceUtil;
import vn.graybee.utils.TextUtils;

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

    private static final EnumSet<DirectoryStatus> ALLOWED_TRANSITIONS = EnumSet.of(
            DirectoryStatus.ACTIVE,
            DirectoryStatus.INACTIVE,
            DirectoryStatus.DELETED,
            DirectoryStatus.REMOVED
    );

    private final MessageSourceUtil messageSourceUtil;

    private final SubCategoryRepository subCategoryRepository;

    private final CategorySubCategoryRepository categorySubCategoryRepository;

    private final CategoryManufacturerRepository categoryManufacturerRepository;

    private final ManufacturerRepository manufacturerRepository;

    private final CategoryRepository categoryRepository;

    private final SubCategoryServices subCategoryServices;

    private final RedisProductService redisProductService;

    private final ICategoryClassificationService iCategoryClassificationService;

    public CategoryServiceImp(MessageSourceUtil messageSourceUtil, SubCategoryRepository subCategoryRepository, CategorySubCategoryRepository categorySubCategoryRepository, CategoryManufacturerRepository categoryManufacturerRepository, ManufacturerRepository manufacturerRepository, CategoryRepository categoryRepository, SubCategoryServices subCategoryServices, RedisProductService redisProductService, ICategoryClassificationService iCategoryClassificationService) {
        this.messageSourceUtil = messageSourceUtil;
        this.subCategoryRepository = subCategoryRepository;
        this.categorySubCategoryRepository = categorySubCategoryRepository;
        this.categoryManufacturerRepository = categoryManufacturerRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryServices = subCategoryServices;
        this.redisProductService = redisProductService;
        this.iCategoryClassificationService = iCategoryClassificationService;
    }

    public List<SubcategoryDto> validateSubcategoryNames(List<String> subcategoryNames) {
        if (subcategoryNames == null || subcategoryNames.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> requestedNames = subcategoryNames.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        List<SubcategoryDto> subcategories = subCategoryRepository.findByNamesAndStatus(subcategoryNames, DirectoryStatus.ACTIVE);

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

    public List<ManufacturerDto> validateManufacturerNames(List<String> manufacturerNames) {

        if (manufacturerNames == null || manufacturerNames.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> requestedNames = manufacturerNames.stream().map(String::toLowerCase).collect(Collectors.toSet());

        List<ManufacturerDto> manufacturers = manufacturerRepository.findByNamesAndStatus(manufacturerNames, DirectoryStatus.ACTIVE);
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
    public BasicMessageResponse<CategoryResponse> create(CategoryCreateRequest request) {

        if (categoryRepository.validateName(request.getName()).isPresent()) {
            throw new BusinessCustomException(ConstantCategory.name, messageSourceUtil.get("category.error.name_exists", new Object[]{request.getName()}));
        }

        List<SubcategoryDto> subcategories = validateSubcategoryNames(request.getSubcategories());

        List<ManufacturerDto> manufacturers = validateManufacturerNames(request.getManufacturers());

        Category category = new Category();
        category.setName(TextUtils.capitalize(request.getName()));
        category.setStatus(DirectoryStatus.INACTIVE);
        category = categoryRepository.save(category);

        if (!subcategories.isEmpty() || !manufacturers.isEmpty()) {
            List<Integer> subcategoryIds = subcategories.stream().map(SubcategoryDto::getId).toList();
            List<Integer> manufacturerIds = manufacturers.stream().map(ManufacturerDto::getId).toList();

            iCategoryClassificationService.createCategoryClassification(category.getId(), subcategoryIds, manufacturerIds);

        }

        CategoryResponse categoryResponse = new CategoryResponse(
                category
        );
        categoryResponse.setSubcategories(subcategories);
        categoryResponse.setManufacturers(manufacturers);

        return new BasicMessageResponse<>(201, messageSourceUtil.get("category.success_create", new Object[]{category.getName()}), categoryResponse);
    }

    @Override
    public BasicMessageResponse<CategoryResponse> findById(int id) {
        CategoryResponse category = categoryRepository.getById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.error.does_not_exists")));

        List<SubcategoryDto> subcategories = categorySubCategoryRepository.findByCategoryId(category.getId());
        List<ManufacturerDto> manufacturers = categoryManufacturerRepository.findByCategoryId(category.getId());

        category.setManufacturers(manufacturers);
        category.setSubcategories(subcategories);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_find_by_id"), category);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {

        CategoryProductCountResponse category = categoryRepository.getProductCountById(id)
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
    public BasicMessageResponse<CategoryResponse> update(int id, CategoryUpdateRequest request) {

        DirectoryStatus status = DirectoryStatus.getStatus(request.getStatus(), messageSourceUtil);

        List<SubcategoryDto> subcategories = validateSubcategoryNames(request.getSubcategories());

        List<ManufacturerDto> manufacturers = validateManufacturerNames(request.getManufacturers());

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
            List<Integer> subcategoryIds = subcategories.stream().map(SubcategoryDto::getId).toList();
            List<Integer> manufacturerIds = manufacturers.stream().map(ManufacturerDto::getId).toList();

            iCategoryClassificationService.updateCategoryClassification(category.getId(), subcategoryIds, manufacturerIds);

        }

        CategoryResponse categoryResponse = new CategoryResponse(
                category
        );
        categoryResponse.setSubcategories(subcategories);
        categoryResponse.setManufacturers(manufacturers);

//        redisProductService.deleteProductListPattern(category.getName());

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_update", new Object[]{category.getName()}), categoryResponse);

    }

    @Override
    public BasicMessageResponse<List<CategoryResponse>> fetchCategories_ADMIN() {

        List<CategoryResponse> categories = categoryRepository.fetchAll();

        if (categories.isEmpty()) {
            return new BasicMessageResponse<>(200, messageSourceUtil.get("category.empty_list"), categories);
        }

        List<Integer> categoryIds = categories.stream().map(CategoryResponse::getId).toList();
        List<CategoryManuDto> categoryManufacturer = categoryManufacturerRepository.findManufacturersByCategoryId_ADMIN(categoryIds);
        List<CategorySubDto> categorySubcategory = categorySubCategoryRepository.findSubcategoriesByCategoryId_ADMIN(categoryIds);

        Map<Integer, List<ManufacturerDto>> CateManuMap = categoryManufacturer.stream()
                .collect(Collectors.groupingBy(CategoryManuDto::getCategoryId, Collectors.mapping(manufacturer -> new ManufacturerDto(manufacturer.getManufacturerId(), manufacturer.getManufacturerName()), Collectors.toList())));

        Map<Integer, List<SubcategoryDto>> CateSubMap = categorySubcategory.stream()
                .collect(Collectors.groupingBy(CategorySubDto::getCategoryId, Collectors.mapping(subcategory -> new SubcategoryDto(subcategory.getSubCategoryId(), subcategory.getSubcategoryName()), Collectors.toList())));

        categories.forEach(category -> {
            category.setManufacturers(CateManuMap.getOrDefault(category.getId(), Collections.emptyList()));
            category.setSubcategories(CateSubMap.getOrDefault(category.getId(), Collections.emptyList()));
        });

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_fetch_list"), categories);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CategoryManufacturerIdResponse> deleteRelationByCategoryIdAndManufacturerId(int categoryId, int manufacturerId) {

        String categoryName = categoryRepository.getNameById(categoryId)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.error.does_not_exists")));

        CategoryManufacturerIdResponse response = categoryManufacturerRepository.findManufacturerIdWithCategoryId(categoryId, manufacturerId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.relation_manufacturer_not_linked", new Object[]{categoryName})));

        categoryManufacturerRepository.deleteManufacturerByIdAndCategoryById(categoryId, manufacturerId);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_remove_manufacturer_relation", new Object[]{categoryName}), response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CategorySubcategoryIdResponse> deleteRelationBySubcategoryByCategoryId(int categoryId, int subcategoryId) {

        String categoryName = categoryRepository.getNameById(categoryId)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.error.does_not_exists")));

        CategorySubcategoryIdResponse response = categorySubCategoryRepository.findSubcategoryIdWithCategoryId(categoryId, subcategoryId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.relation_subcategory_not_linked", new Object[]{categoryName})));

        categorySubCategoryRepository.deleteSubcategoryByCategoryById(categoryId, subcategoryId);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_remove_subcategory_relation", new Object[]{categoryName}), response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<UpdateStatusResponse> updateStatusById(int id, String status) {

        DirectoryStatus newStatus = DirectoryStatus.getStatus(status, messageSourceUtil);

        CategoryStatusDto category = categoryRepository.findNameAndStatusById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.error.does_not_exists")));

        if (category.getStatus() == newStatus) {
            return new BasicMessageResponse<>(200, messageSourceUtil.get("category.status_not_changed", new Object[]{category.getName()}), null);
        }

        if (category.getStatus() == DirectoryStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.error.removed"));
        }

        if (category.getStatus() == DirectoryStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.error.deleted"));
        }

        if (newStatus == DirectoryStatus.PENDING
                && (category.getStatus() == DirectoryStatus.ACTIVE || category.getStatus() == DirectoryStatus.INACTIVE)) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.status.cannot_return"));
        }

        categoryRepository.updateStatusById(id, newStatus);

        UpdateStatusResponse response = new UpdateStatusResponse(category.getId(), newStatus, LocalDateTime.now());

        redisProductService.deleteProductListPattern(category.getName());

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_update_status", new Object[]{category.getName(), status}), response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CategoryResponse> restoreById(int id, UserPrincipal userPrincipal) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.error.does_not_exists")));

        DirectoryStatus currentStatus = category.getStatus();

        if (userPrincipal != null && !userPrincipal.getUser().isSuperAdmin() && currentStatus == DirectoryStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("common.permission.super_admin_required"));
        }

        if (currentStatus != DirectoryStatus.DELETED && currentStatus != DirectoryStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.error.not_removed", new Object[]{category.getName()}));
        }

        categoryRepository.updateStatusById(id, DirectoryStatus.INACTIVE);

        CategoryResponse categoryResponse = new CategoryResponse(category);

        List<SubcategoryDto> subcategories = categorySubCategoryRepository.findByCategoryId(category.getId());
        List<ManufacturerDto> manufacturers = categoryManufacturerRepository.findByCategoryId(category.getId());

        categoryResponse.setSubcategories(subcategories);
        categoryResponse.setManufacturers(manufacturers);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_restore", new Object[]{category.getName()}), categoryResponse);
    }

    @Override
    public BasicMessageResponse<List<SidebarDto>> getSidebar() {

        List<SidebarDto> sidebar = categoryRepository.getSidebar();

        if (sidebar.isEmpty()) {
            return new BasicMessageResponse<>(200, messageSourceUtil.get("common.empty_list"), sidebar);
        }

        List<Integer> categoryIds = sidebar.stream().map(SidebarDto::getId).toList();

        List<CategoryManuDto> manuDtos = categoryManufacturerRepository.findManufacturersByCategoryId_ADMIN(categoryIds);
        List<CategorySubDto> subDtos = categorySubCategoryRepository.findSubcategoriesByCategoryId_ADMIN(categoryIds);

        List<vn.graybee.response.publics.sidebar.SubcategoryDto> subcategories = subCategoryServices.getForSidebar();

        Map<Integer, List<ManufacturerDto>> CateManuMap = manuDtos.stream()
                .collect(Collectors.groupingBy(CategoryManuDto::getCategoryId, Collectors.mapping(manufacturer -> new ManufacturerDto(manufacturer.getManufacturerId(), manufacturer.getManufacturerName()), Collectors.toList())));


        Map<Integer, vn.graybee.response.publics.sidebar.SubcategoryDto> subIdToDtoMap = subcategories.stream()
                .collect(Collectors.toMap(vn.graybee.response.publics.sidebar.SubcategoryDto::getId, Function.identity()));


        Map<Integer, List<vn.graybee.response.publics.sidebar.SubcategoryDto>> categoryToSubDtos = subDtos.stream()
                .collect(Collectors.groupingBy(
                        CategorySubDto::getCategoryId,
                        Collectors.mapping(sub -> subIdToDtoMap.get(sub.getSubCategoryId()), Collectors.toList())
                ));


        sidebar.forEach(category -> {
            category.setManufacturers(CateManuMap.getOrDefault(category.getId(), Collections.emptyList()));
            category.setSubcategories(categoryToSubDtos.getOrDefault(category.getId(), Collections.emptyList()));

        });

        return new BasicMessageResponse<>(200, null, sidebar);
    }


}
