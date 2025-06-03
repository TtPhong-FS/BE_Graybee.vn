package vn.graybee.modules.catalog.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.constants.ConstantCategory;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.common.utils.SlugUtil;
import vn.graybee.common.utils.TextUtils;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.catalog.dto.request.CategoryRequest;
import vn.graybee.modules.catalog.dto.response.CategoryDto;
import vn.graybee.modules.catalog.dto.response.CategoryProductCountDto;
import vn.graybee.modules.catalog.dto.response.CategorySimpleDto;
import vn.graybee.modules.catalog.dto.response.CategorySlugWithParentId;
import vn.graybee.modules.catalog.dto.response.CategorySummaryDto;
import vn.graybee.modules.catalog.dto.response.ChildrenIdAndParentId;
import vn.graybee.modules.catalog.dto.response.SidebarDto;
import vn.graybee.modules.catalog.dto.response.UpdateStatusDto;
import vn.graybee.modules.catalog.enums.CategoryStatus;
import vn.graybee.modules.catalog.enums.CategoryType;
import vn.graybee.modules.catalog.model.Category;
import vn.graybee.modules.catalog.repository.CategoryRepository;
import vn.graybee.modules.catalog.service.AttributeService;
import vn.graybee.modules.catalog.service.CategoryService;
import vn.graybee.modules.product.service.RedisProductService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImp implements CategoryService {

    private static final String CACHE_KEY = "manufacturers:categoryId:";

    private static final EnumSet<CategoryStatus> ALLOWED_TRANSITIONS = EnumSet.of(
            CategoryStatus.ACTIVE,
            CategoryStatus.INACTIVE,
            CategoryStatus.REMOVED
    );

    private final MessageSourceUtil messageSourceUtil;

    private final CategoryRepository categoryRepository;

    private final RedisProductService redisProductService;

    private final AttributeService attributeService;

    public CategoryServiceImp(MessageSourceUtil messageSourceUtil, CategoryRepository categoryRepository, RedisProductService redisProductService, @Lazy AttributeService attributeService) {
        this.messageSourceUtil = messageSourceUtil;
        this.categoryRepository = categoryRepository;
        this.redisProductService = redisProductService;
        this.attributeService = attributeService;
    }

    private String getCategoryType(String type) {
        return CategoryType.getType(type, messageSourceUtil).name();
    }

    private CategoryStatus getCategoryStatus(String status) {
        return CategoryStatus.getStatus(status, messageSourceUtil);
    }

    private CategorySimpleDto getCategorySimpleDto(Category category) {
        return new CategorySimpleDto(
                category.getId(),
                category.getSlug(),
                category.getName(),
                category.getParentId(),
                category.getProductCount(),
                category.getStatus(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BasicMessageResponse<CategorySimpleDto> createCategory(CategoryRequest request) {

        Long parentId = null;
        if (request.getParentName() != null) {
            parentId = getIdByName(request.getParentName());
        }

        if (categoryRepository.existsByName(request.getName())) {
            throw new BusinessCustomException(ConstantCategory.name, messageSourceUtil.get("category.name_exists", new Object[]{request.getName()}));
        }

        CategoryStatus categoryStatus = getCategoryStatus(request.getStatus());

        Category category = new Category();
        category.setName(TextUtils.capitalize(request.getName()));
        category.setSlug(SlugUtil.toSlug(request.getName()));
        category.setParentId(parentId);
        category.setStatus(categoryStatus);
        category = categoryRepository.save(category);

//      Save Attribute after Category created.
        attributeService.saveAttributesByCategoryId(category.getId(), request.getAttributes());

        CategorySimpleDto categorySimpleDto = getCategorySimpleDto(category);

        return new BasicMessageResponse<>(201, messageSourceUtil.get("category.success_create", new Object[]{category.getName()}), categorySimpleDto);
    }

    @Override
    public BasicMessageResponse<CategoryDto> getCategoryById(Long id) {
        CategoryDto category = categoryRepository.findCategoryDtoById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.does_not_exists")));

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_find_by_id"), category);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Long> deleteById(Long id) {

        CategoryProductCountDto category = categoryRepository.getProductCountById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.not_found", new Object[]{id})));

        if (category.getProductCount() > 0) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.in_use"));
        }

        categoryRepository.deleteById(id);

        redisProductService.deleteProductListPattern(category.getName());

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_delete", new Object[]{category.getName()}), category.getId());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<List<Long>> deleteByIds(List<Long> ids) {
        System.out.println(ids);
        return new BasicMessageResponse<>(200, ConstantCategory.success_delete_multiple, ids);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ChildrenIdAndParentId> removeChildrenByParentIdAndChildrenId(Long parentId, Long childrenId) {

        if (parentId == null || childrenId == null) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.parent_id_or_children_id_null"));
        }

        checkExistsById(parentId);

        if (!categoryRepository.checkExistsRelationByParentIdAndChildrenId(parentId, childrenId)) {
            throw new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("category.relation_not_exists"));
        }

        String childrenName = getNameById(childrenId);
        String parentName = getNameById(parentId);

        categoryRepository.removeChildrenByParentIdAndChildrenId(parentId, childrenId);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_remove_children", new Object[]{childrenName, parentName}),
                new ChildrenIdAndParentId(childrenId, parentId));
    }

    @Override
    public void checkExistsById(Long id) {
        if (id != null && !categoryRepository.existsById(id)) {
            throw new CustomNotFoundException(ConstantGeneral.not_found, messageSourceUtil.get("category.not_found", new Object[]{id}));
        }
    }

    private Long getIdByName(String name) {
        return categoryRepository.findIdByName(name)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.parentName, messageSourceUtil.get("category.does_not_exists", new Object[]{name})));

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CategorySimpleDto> updateCategory(Long id, CategoryRequest request) {


        Long parentId = null;
        if (request.getParentName() != null) {
            parentId = getIdByName(request.getParentName());
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.not_found, messageSourceUtil.get("category.not_found", new Object[]{id})));

        if (!category.getName().equals(request.getName()) && categoryRepository.existsByNameAndNotId(request.getName(), category.getId())) {
            throw new BusinessCustomException(ConstantCategory.name, messageSourceUtil.get("category.name_exists", new Object[]{request.getName()}));
        }

        if (category.getProductCount() > 0 && !category.getName().equals(request.getName())) {
            throw new BusinessCustomException(ConstantCategory.name, messageSourceUtil.get("category.in_use", new Object[]{category.getName()}));
        }

        if (parentId != null && parentId.equals(category.getId())) {
            throw new BusinessCustomException(ConstantCategory.parentName, messageSourceUtil.get("category.parent_id_cannot_be_self"));
        }

        CategoryStatus categoryStatus = getCategoryStatus(request.getStatus());


        category.setParentId(parentId);
        category.setSlug(SlugUtil.toSlug(request.getName()));
        category.setStatus(categoryStatus);
        category.setUpdatedAt(LocalDateTime.now());

        category = categoryRepository.save(category);

        CategorySimpleDto categorySimpleDto = getCategorySimpleDto(category);

//        redisProductService.deleteProductListPattern(category.getName());

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_update", new Object[]{category.getName()}), categorySimpleDto);

    }

    @Override
    public BasicMessageResponse<List<CategoryDto>> getAllCategoryDtoForDashboard() {

        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            return new BasicMessageResponse<>(200, messageSourceUtil.get("category.empty_list"), Collections.emptyList());
        }

        Map<Long, CategoryDto> categoryDtoMap = new HashMap<>();

        for (Category cat : categories) {
            CategoryDto dto = new CategoryDto();
            dto.setId(cat.getId());
            dto.setParentId(cat.getParentId());
            dto.setName(cat.getName());
            dto.setSlug(cat.getSlug());
            dto.setStatus(cat.getStatus());
            dto.setProductCount(cat.getProductCount());
            dto.setCreatedAt(cat.getCreatedAt());
            dto.setUpdatedAt(cat.getUpdatedAt());
            categoryDtoMap.put(cat.getId(), dto);
        }

        List<CategoryDto> roots = new ArrayList<>();
        for (Category cat : categories) {
            CategoryDto dto = categoryDtoMap.get(cat.getId());
            if (cat.getParentId() == null) {
                roots.add(dto);
            } else {
                CategoryDto parent = categoryDtoMap.get(cat.getParentId());
                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_fetch_list"), roots);

    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<UpdateStatusDto> updateStatusById(Long id, String status) {

        CategoryStatus newStatus = CategoryStatus.getStatus(status, messageSourceUtil);

        CategorySummaryDto category = categoryRepository.findNameAndStatusById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.not_found, messageSourceUtil.get("category.not_found", new Object[]{id})));

        if (category.getStatus() == newStatus) {
            return new BasicMessageResponse<>(200, messageSourceUtil.get("category.status_not_changed", new Object[]{category.getName()}), null);
        }

        if (category.getStatus() == CategoryStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.in_trash"));
        }


        if (newStatus == CategoryStatus.PENDING
                && (category.getStatus() == CategoryStatus.ACTIVE || category.getStatus() == CategoryStatus.INACTIVE)) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.status.cannot_return"));
        }

        categoryRepository.updateStatusById(id, newStatus);

        UpdateStatusDto response = new UpdateStatusDto(category.getId(), newStatus, LocalDateTime.now());

        redisProductService.deleteProductListPattern(category.getName());

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_update_status", new Object[]{category.getName(), status}), response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CategoryDto> restoreById(Long id, UserDetail userDetail) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.not_found, messageSourceUtil.get("category.not_found", new Object[]{id})));

        CategoryStatus currentStatus = category.getStatus();
        if (userDetail != null && !userDetail.user().isSuperAdmin() && currentStatus == CategoryStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("auth.not_super_admin"));
        }

        if (currentStatus != CategoryStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get("category.not_in_trash", new Object[]{category.getName()}));
        }

        categoryRepository.updateStatusById(id, CategoryStatus.INACTIVE);

        CategoryDto categoryDto = new CategoryDto(category);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("category.success_restore", new Object[]{category.getName()}), categoryDto);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void updateProductCount(Long id, boolean isIncrease) {
        if (isIncrease) {
            categoryRepository.increaseProductCountById(id);
        } else {
            categoryRepository.decreaseProductCountById(id);
        }
    }

    private String getNameById(Long id) {
        return categoryRepository.findNameById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.not_found, messageSourceUtil.get("category.not_found", new Object[]{id})));
    }

    @Override
    public CategorySummaryDto getCategorySummaryByName(String name) {

        CategorySummaryDto categorySummaryDto = categoryRepository.findCategorySummaryDtoByName(name)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.not_found, messageSourceUtil.get("category.not_found")));

        if (categorySummaryDto.getStatus().equals(CategoryStatus.REMOVED)) {
            throw new BusinessCustomException(ConstantCategory.productType, messageSourceUtil.get("category.in_trash", new Object[]{categorySummaryDto.getName()}));
        }

        return categorySummaryDto;
    }

    @Override
    public BasicMessageResponse<List<SidebarDto>> getSidebar() {

        List<CategorySlugWithParentId> categorySlugWithParentIds = categoryRepository.findCategoryTreeDto();

        Map<Long, SidebarDto> categoryMap = new HashMap<>();

        for (CategorySlugWithParentId category : categorySlugWithParentIds) {
            categoryMap.put(category.getId(), new SidebarDto(category.getSlug(), category.getName(), new ArrayList<>()));
        }

        List<SidebarDto> roots = new ArrayList<>();

        for (CategorySlugWithParentId category : categorySlugWithParentIds) {
            if (category.getParentId() == null) {
                roots.add(categoryMap.get(category.getId()));
            } else {
                SidebarDto parentCategory = categoryMap.get(category.getParentId());
                if (parentCategory != null) {
                    parentCategory.getChildren().add(categoryMap.get(category.getId()));
                }
            }
        }

        return new BasicMessageResponse<>(200, null, roots);
    }


}
