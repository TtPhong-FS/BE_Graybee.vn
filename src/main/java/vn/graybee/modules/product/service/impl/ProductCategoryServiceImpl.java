package vn.graybee.modules.product.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.exception.MultipleException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.catalog.dto.response.CategoryBasicDto;
import vn.graybee.modules.catalog.dto.response.CategorySummaryDto;
import vn.graybee.modules.catalog.enums.CategoryType;
import vn.graybee.modules.catalog.service.CategoryService;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;
import vn.graybee.modules.product.model.ProductCategory;
import vn.graybee.modules.product.repository.ProductCategoryRepository;
import vn.graybee.modules.product.service.ProductCategoryService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    private final CategoryService categoryService;

    private final MessageSourceUtil messageSourceUtil;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createProductCategoryByTags(Long productId, List<String> tags) {

        List<CategorySummaryDto> categorySummaryDtos = categoryService.findCategorySummaryDtoByNames(tags);

        Map<String, CategorySummaryDto> nameToDtoMap = categorySummaryDtos.stream()
                .collect(Collectors.toMap(dto -> dto.getName().trim(), Function.identity()));

        checkCategoryExistsAndThrow(nameToDtoMap, tags);

        List<ProductCategory> productCategories = new ArrayList<>();
        for (CategorySummaryDto tag : categorySummaryDtos) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductId(productId);
            productCategory.setTagId(tag.getId());

            productCategories.add(productCategory);
        }

        productCategoryRepository.saveAll(productCategories);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateProductCategoryByTags(Long productId, List<String> tags) {

        List<CategorySummaryDto> categorySummaryDtos = categoryService.findCategorySummaryDtoByNames(tags);

        Map<String, CategorySummaryDto> nameToDtoMap = categorySummaryDtos.stream()
                .collect(Collectors.toMap(dto -> dto.getName().toLowerCase().trim(), Function.identity()));

        checkCategoryExistsAndThrow(nameToDtoMap, tags);

        syncProductCategories(productId, new HashSet<>(nameToDtoMap.values().stream().map(CategorySummaryDto::getId).collect(Collectors.toSet())));
    }

    @Override
    public CategoryBasicDto findCategoryBasicDtoByProductId(long productId) {
        return productCategoryRepository.findCategoryBasicDtoByProductId(productId)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("product.primary.category.not.found", new Object[]{productId})));
    }

    @Override
    public Page<ProductBasicResponse> findProductByTagSlug(String tagSlug, Pageable pageable) {
        categoryService.checkExistsBySlug(tagSlug);
        return productCategoryRepository.findProductByTagSlug(tagSlug, pageable);
    }


    private void syncProductCategories(Long productId, Set<Long> newCategoryIds) {
        List<ProductCategory> currentRelations = productCategoryRepository.findAllByProductId(productId);

        Set<Long> existingCategoryIds = currentRelations.stream()
                .map(ProductCategory::getTagId)
                .collect(Collectors.toSet());

        Set<Long> categoryIdsAdd = new HashSet<>(newCategoryIds);
        categoryIdsAdd.removeAll(existingCategoryIds);

        Set<Long> categoryIdsRemove = new HashSet<>(existingCategoryIds);
        categoryIdsRemove.removeAll(newCategoryIds);

        if (!categoryIdsRemove.isEmpty()) {
            productCategoryRepository.deleteByProductIdAndCategoryIdIn(productId, categoryIdsRemove);
        }

        if (!categoryIdsAdd.isEmpty()) {
            List<ProductCategory> newRelations = categoryIdsAdd.stream()
                    .map(tagId -> {
                        ProductCategory pc = new ProductCategory();
                        pc.setProductId(productId);
                        pc.setTagId(tagId);
                        return pc;
                    })
                    .collect(Collectors.toList());

            productCategoryRepository.saveAll(newRelations);
        }
    }

    private void checkCategoryExistsAndThrow(Map<String, CategorySummaryDto> nameToDtoMap,
                                             List<String> tags) {

        Map<String, String> errorMessages = new LinkedHashMap<>();

        // Check tags
        if (tags != null && !tags.isEmpty()) {
            List<String> notFoundTags = new ArrayList<>();
            List<String> invalidTypeTags = new ArrayList<>();

            for (String tag : tags) {
                String key = tag.trim();
                CategorySummaryDto dto = nameToDtoMap.get(key);
                if (dto == null) {
                    notFoundTags.add(key);
                } else if (dto.getType() != CategoryType.TAG) {
                    invalidTypeTags.add(key);
                }
            }

            if (!notFoundTags.isEmpty()) {
                errorMessages.put(Constants.Product.tagNames,
                        messageSourceUtil.get("catalog.category.not.found", new Object[]{String.join(", ", notFoundTags)}));
            }

            if (!invalidTypeTags.isEmpty()) {
                errorMessages.put(Constants.Product.tagNames,
                        messageSourceUtil.get("catalog.category.invalid.type", new Object[]{String.join(", ", invalidTypeTags), CategoryType.TAG}));
            }
        }

        if (!errorMessages.isEmpty()) {
            throw new MultipleException(errorMessages);
        }
    }

}
