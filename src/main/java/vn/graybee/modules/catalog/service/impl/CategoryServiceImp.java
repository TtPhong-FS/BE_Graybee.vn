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
import vn.graybee.modules.catalog.dto.request.CategoryRequest;
import vn.graybee.modules.catalog.dto.response.CategoryDto;
import vn.graybee.modules.catalog.dto.response.CategoryIdActiveResponse;
import vn.graybee.modules.catalog.dto.response.CategoryIdNameDto;
import vn.graybee.modules.catalog.dto.response.CategorySlugWithParentId;
import vn.graybee.modules.catalog.dto.response.CategorySummaryDto;
import vn.graybee.modules.catalog.dto.response.CategoryUpdateDto;
import vn.graybee.modules.catalog.dto.response.SidebarDto;
import vn.graybee.modules.catalog.enums.CategoryType;
import vn.graybee.modules.catalog.model.Category;
import vn.graybee.modules.catalog.repository.CategoryRepository;
import vn.graybee.modules.catalog.service.CategoryService;
import vn.graybee.modules.product.service.RedisProductService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryServiceImp implements CategoryService {


    private final MessageSourceUtil messageSourceUtil;

    private final CategoryRepository categoryRepository;

    private final RedisProductService redisProductService;


    private CategoryType getCategoryType(String type) {
        return CategoryType.getType(type, messageSourceUtil);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Category createCategory(CategoryRequest request) {
        CategoryType type = getCategoryType(request.getCategoryType());

        if (categoryRepository.existsByName(request.getName().trim())) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("catalog.category.name.exists", new Object[]{request.getName()}));
        }

        Long parentId = checkWithParentName(request.getParentName());

        Category category = new Category();
        category.setName(TextUtils.capitalizeEachWord(request.getName()));
        category.setParentId(parentId);
        if (request.getSlug() == null || request.getSlug().isEmpty()) {
            category.setSlug(null);
        } else {
            category.setSlug(SlugUtil.toSlug(request.getSlug()));
        }

        category.setCategoryType(type);
        category.setActive(request.isActive());
        return categoryRepository.save(category);


    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Long deleteById(Long id) {

        categoryRepository.findCategorySummaryDtoByNameOrId(null, id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found")));

        categoryRepository.deleteById(id);

        return id;
    }


    @Override
    public void checkExistsById(Long id) {
        if (id != null && !categoryRepository.existsById(id)) {
            throw new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found"));
        }
    }

    private Long checkWithParentName(String parentName) {
        if (parentName == null || parentName.isEmpty()) {
            return null;
        }
        return categoryRepository.findIdByName(parentName).orElseThrow(() -> new CustomNotFoundException(Constants.Category.parentName, messageSourceUtil.get("catalog.category.not.found", new Object[]{parentName})));
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Category updateCategory(Long id, CategoryRequest request) {
        CategoryType type = getCategoryType(request.getCategoryType());

        Long parentId = checkWithParentName(request.getParentName());

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found")));

        if (!category.getName().equals(request.getName()) && categoryRepository.existsByNameAndNotId(request.getName(), category.getId())) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("catalog.category.name.exists", new Object[]{request.getName()}));
        }

        if (parentId != null && parentId.equals(category.getId())) {
            throw new BusinessCustomException(Constants.Category.parentName, messageSourceUtil.get("catalog.category.parent_id_cannot_be_self"));
        }

        category.setParentId(parentId);
        category.setName(TextUtils.capitalizeEachWord(request.getName()));
        if (request.getSlug() == null || request.getSlug().isEmpty()) {
            category.setSlug(null);
        } else {
            category.setSlug(SlugUtil.toSlug(request.getSlug()));
        }
        category.setCategoryType(type);
        category.setActive(request.isActive());
        category.setUpdatedAt(LocalDateTime.now());

        return categoryRepository.save(category);
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
            dto.setCategoryType(cat.getCategoryType());
            dto.setSlug(cat.getSlug());
            dto.setActive(cat.isActive());
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
    public List<SidebarDto> getSidebar() {

        List<CategorySlugWithParentId> categorySlugWithParentIds = categoryRepository.findCategoryTreeDto();

        Map<Long, SidebarDto> categoryMap = new HashMap<>();

        for (CategorySlugWithParentId category : categorySlugWithParentIds) {
            categoryMap.put(category.getId(), new SidebarDto(category.getSlug(), category.getName(), category.getType().name().toLowerCase(), new ArrayList<>()));
        }

        List<SidebarDto> roots = new ArrayList<>();

        for (CategorySlugWithParentId category : categorySlugWithParentIds) {
            if (category.getParentId() == null && category.getType() == CategoryType.CATEGORY) {
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
    public String findNameById(long id) {
        return categoryRepository.findNameById(id).orElseThrow(() -> new CustomNotFoundException(Constants.Product.categoryName, messageSourceUtil.get("catalog.category.not.found")));
    }


    @Override
    public List<CategoryIdNameDto> getCategoryIdNameDtos(List<String> categoryNames) {

        if (categoryNames == null || categoryNames.isEmpty()) {
            return Collections.emptyList();
        }

        List<CategoryIdNameDto> categoryIdNameDtos = categoryRepository.findCategoryIdNameDtoByNames(categoryNames);

        Set<String> foundNamesLower = categoryIdNameDtos.stream().map(c -> c.getName().trim().toLowerCase()).collect(Collectors.toSet());
        Set<String> requestNamesLower = new HashSet<>(categoryNames.stream().map(name -> name.trim().toLowerCase()).toList());
        Set<String> notFoundNames = new HashSet<>(requestNamesLower);
        notFoundNames.removeAll(foundNamesLower);

        if (!notFoundNames.isEmpty()) {
            throw new CustomNotFoundException(Constants.Attribute.categoryNames, messageSourceUtil.get("catalog.category.not.found", new Object[]{String.join(", ", notFoundNames)}));
        }

        return categoryIdNameDtos;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CategoryIdActiveResponse toggleActiveById(long id) {

        Boolean active = categoryRepository.getActiveById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.category.not.found")));

        categoryRepository.toggleActiveById(id);

        return new CategoryIdActiveResponse(id, !active);
    }

    @Override
    public CategorySummaryDto findCategorySummaryDtoByName(String name, String prefix) {
        return categoryRepository.findCategorySummaryDtoByNameOrId(name, null).orElseThrow(() -> new CustomNotFoundException(prefix, messageSourceUtil.get("catalog.category.not.found", new Object[]{name})));
    }

    @Override
    public CategorySummaryDto checkType(String name, CategoryType type) {

        CategorySummaryDto category = new CategorySummaryDto();

        switch (type) {
            case CATEGORY:
                category = findCategorySummaryDtoByName(name, Constants.Product.categoryName);
                if (category.getType() != CategoryType.CATEGORY) {
                    throw new CustomNotFoundException(Constants.Product.categoryName, "Danh mục " + category.getName() + " phải thuộc loại 'CATEGORY' nhưng nhận về loại " + category.getType());
                }
                break;
            case BRAND:
                category = findCategorySummaryDtoByName(name, Constants.Product.brandName);
                if (category.getType() != CategoryType.BRAND) {
                    throw new CustomNotFoundException(Constants.Product.categoryName, "Danh mục " + category.getName() + " phải thuộc loại 'BRAND' nhưng nhận về loại " + category.getType());
                }
                break;

        }
        return category;
    }

}
