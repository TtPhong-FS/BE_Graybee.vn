package vn.graybee.modules.catalog.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.common.utils.SlugUtil;
import vn.graybee.common.utils.TextUtils;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.catalog.dto.request.CategoryRequest;
import vn.graybee.modules.catalog.dto.response.CategoryDto;
import vn.graybee.modules.catalog.dto.response.CategorySimpleDto;
import vn.graybee.modules.catalog.dto.response.CategorySlugWithParentId;
import vn.graybee.modules.catalog.dto.response.CategorySummaryDto;
import vn.graybee.modules.catalog.dto.response.CategoryUpdateDto;
import vn.graybee.modules.catalog.dto.response.SidebarDto;
import vn.graybee.modules.catalog.dto.response.UpdateStatusDto;
import vn.graybee.modules.catalog.enums.CategoryStatus;
import vn.graybee.modules.catalog.enums.CategoryType;
import vn.graybee.modules.catalog.model.Category;
import vn.graybee.modules.catalog.repository.CategoryRepository;
import vn.graybee.modules.catalog.service.CategoryService;
import vn.graybee.modules.product.service.ProductClassifyViewService;
import vn.graybee.modules.product.service.RedisProductService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class CategoryServiceImp implements CategoryService {


    private final MessageSourceUtil messageSourceUtil;

    private final CategoryRepository categoryRepository;

    private final RedisProductService redisProductService;

    private final ProductClassifyViewService productClassifyViewService;

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
                category.getType(),
                category.getStatus(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategorySimpleDto createCategory(CategoryRequest request) {

        Long parentId = null;
        if (request.getParentName() != null && !request.getParentName().isEmpty()) {
            parentId = getIdByName(request.getParentName());
        }

        if (categoryRepository.existsByName(request.getName())) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("catalog.category.name.exists", new Object[]{request.getName()}));
        }

        String type = getCategoryType(request.getType());

        CategoryStatus categoryStatus = getCategoryStatus(request.getStatus());

        Category category = new Category();
        category.setName(TextUtils.capitalize(request.getName()));

        if (request.getSlug() == null || request.getSlug().isEmpty()) {
            category.setSlug(null);
        } else {
            category.setSlug(SlugUtil.toSlug(request.getSlug()));
        }

        category.setType(type);
        category.setParentId(parentId);
        category.setStatus(categoryStatus);
        category = categoryRepository.save(category);

        return getCategorySimpleDto(category);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Long deleteById(Long id) {

        CategorySummaryDto categorySummaryDto = categoryRepository.findCategorySummaryDtoByNameOrId(null, id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found")));

        categoryRepository.deleteById(id);

        CategoryType type = CategoryType.valueOf(categorySummaryDto.getType());

        switch (type) {
            case CATEGORY:
                productClassifyViewService.removeCategoryByCategoryName(categorySummaryDto.getName());
                break;
            case BRAND:
                productClassifyViewService.removeBrandByBrandName(categorySummaryDto.getName());
                break;
            case TAG:
                productClassifyViewService.removeTagByTagName(categorySummaryDto.getName());
                break;
            default:
                break;
        }

        redisProductService.deleteProductListPattern(categorySummaryDto.getName());

        return id;
    }


    @Override
    public void checkExistsById(Long id) {
        if (id != null && !categoryRepository.existsById(id)) {
            throw new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found"));
        }
    }

    private Long getIdByName(String name) {
        return categoryRepository.findIdByName(name)
                .orElseThrow(() -> new BusinessCustomException(Constants.Category.parentName, messageSourceUtil.get("catalog.category.does.not.exists", new Object[]{name})));

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CategorySimpleDto updateCategory(Long id, CategoryRequest request) {

        Long parentId = null;
        if (request.getParentName() != null && !request.getParentName().isEmpty()) {
            parentId = getIdByName(request.getParentName());
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found")));

        if (!category.getName().equals(request.getName()) && categoryRepository.existsByNameAndNotId(request.getName(), category.getId())) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("catalog.category.name.exists", new Object[]{request.getName()}));
        }

        if (parentId != null && parentId.equals(category.getId())) {
            throw new BusinessCustomException(Constants.Category.parentName, messageSourceUtil.get("catalog.category.parent_id_cannot_be_self"));
        }

        String type = getCategoryType(request.getType());

        CategoryStatus categoryStatus = getCategoryStatus(request.getStatus());

        category.setParentId(parentId);
        category.setName(TextUtils.capitalizeEachWord(request.getName()));
        if (request.getSlug() == null || request.getSlug().isEmpty()) {
            category.setSlug(null);
        } else {
            category.setSlug(SlugUtil.toSlug(request.getSlug()));
        }
        category.setType(type);
        category.setStatus(categoryStatus);
        category.setUpdatedAt(LocalDateTime.now());

        category = categoryRepository.save(category);

        //        redisProductService.deleteProductListPattern(category.getName());

        return getCategorySimpleDto(category);

    }

    @Override
    public List<CategoryDto> getAllCategoryDtoForDashboard() {

        List<Category> categories = categoryRepository.findAll();

        Map<Long, CategoryDto> categoryDtoMap = new HashMap<>();

        for (Category cat : categories) {
            CategoryDto dto = new CategoryDto();
            dto.setId(cat.getId());
            dto.setParentId(cat.getParentId());
            dto.setName(cat.getName());
            dto.setType(cat.getType());
            dto.setSlug(cat.getSlug());
            dto.setStatus(cat.getStatus());
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

        return roots;

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UpdateStatusDto updateStatusById(Long id, String status) {

        CategoryStatus newStatus = CategoryStatus.getStatus(status, messageSourceUtil);

        CategorySummaryDto category = categoryRepository.findCategorySummaryDtoByNameOrId(null, id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found")));

        if (category.getStatus() == newStatus) {
            return null;
        }

        if (category.getStatus() == CategoryStatus.REMOVED) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("catalog.category.in_trash"));
        }

        if (newStatus == CategoryStatus.PENDING
                && (category.getStatus() == CategoryStatus.ACTIVE || category.getStatus() == CategoryStatus.INACTIVE)) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("catalog.category.status.cannot.return"));
        }

        categoryRepository.updateStatusById(id, newStatus);

        UpdateStatusDto updateStatusDto = new UpdateStatusDto(category.getId(), newStatus, LocalDateTime.now());

        redisProductService.deleteProductListPattern(category.getName());

        return updateStatusDto;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CategoryDto restoreById(Long id, UserDetail userDetail) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found")));

        CategoryStatus currentStatus = category.getStatus();
        if (userDetail != null && !userDetail.user().isSuperAdmin() && currentStatus == CategoryStatus.REMOVED) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("auth.not_super_admin"));
        }

        if (currentStatus != CategoryStatus.REMOVED) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.in_trash", new Object[]{category.getName()}));
        }

        categoryRepository.updateStatusById(id, CategoryStatus.INACTIVE);

        return new CategoryDto(category);
    }

    private String getNameById(Long id) {
        return categoryRepository.findNameById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found")));
    }

    @Override
    public CategorySummaryDto getCategorySummaryByNameOrId(String name, Long id) {

        CategorySummaryDto categorySummaryDto = categoryRepository.findCategorySummaryDtoByNameOrId(name, id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found")));

        if (categorySummaryDto.getStatus().equals(CategoryStatus.REMOVED)) {
            throw new BusinessCustomException(Constants.Product.categoryName, messageSourceUtil.get("catalog.category.in_trash", new Object[]{categorySummaryDto.getName()}));
        }

        return categorySummaryDto;
    }

    @Override
    public List<SidebarDto> getSidebar() {

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

        return roots;
    }

    @Override
    public CategoryUpdateDto getCategoryUpdateDtoById(Long id) {
        return categoryRepository.findCategoryUpdateDtoById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found")));
    }

    @Override
    public void checkExistsBySlug(String categorySlug) {
        if (!categoryRepository.existsBySlug(categorySlug)) {
            throw new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found"));
        }
    }

    @Override
    public List<CategorySummaryDto> findCategorySummaryDtoByNames(List<String> names) {
        return categoryRepository.findCategorySummaryDtoByNames(names);
    }

    @Override
    public Long findCategoryIdByName(String categoryName) {
        return categoryRepository.findIdByName(categoryName)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found", new Object[]{categoryName})));

    }


}
