package vn.graybee.modules.catalog.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.catalog.dto.response.CategoryIdNameDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeIdCategoryIdName;
import vn.graybee.modules.catalog.model.CategoryAttribute;
import vn.graybee.modules.catalog.repository.CategoryAttributeRepository;
import vn.graybee.modules.catalog.service.CategoryAttributeService;
import vn.graybee.modules.catalog.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryAttributeServiceImpl implements CategoryAttributeService {

    private final CategoryAttributeRepository categoryAttributeRepository;

    private final CategoryService categoryService;

    private final MessageSourceUtil messageSourceUtil;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createCategoryAttribute(List<String> categoryNames, long attributeId) {

        if (categoryNames == null || categoryNames.isEmpty()) {
            return;
        }

        List<CategoryIdNameDto> categoryIdNameDtos = categoryService.getCategoryIdNameDtos(categoryNames);

        List<CategoryAttribute> categoryAttributes = new ArrayList<>();
        for (CategoryIdNameDto category : categoryIdNameDtos) {
            CategoryAttribute categoryAttribute = new CategoryAttribute();
            categoryAttribute.setCategoryId(category.getId());
            categoryAttribute.setAttributeId(attributeId);
            categoryAttributes.add(categoryAttribute);
        }

        categoryAttributeRepository.saveAll(categoryAttributes);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String updateCategoryAttribute(List<CategoryIdNameDto> incommingCategory, List<String> categoryNames, long attributeId) {
        if (categoryNames.isEmpty()) {
            categoryAttributeRepository.deleteByAttributeId(attributeId);
            return "clear";
        }
//        List categoryId to new relation
        Set<Long> desiredCategoryIds = incommingCategory.stream()
                .map(CategoryIdNameDto::getId)
                .collect(Collectors.toSet());

        List<CategoryAttribute> existingRelations = categoryAttributeRepository.findAllByAttributeId(attributeId);


//        Fill relation don't need in new relation
        List<CategoryAttribute> relationsToDelete = existingRelations.stream()
                .filter(relation -> !desiredCategoryIds.contains(relation.getCategoryId()))
                .collect(Collectors.toList());

        if (!relationsToDelete.isEmpty()) {
            categoryAttributeRepository.deleteAll(relationsToDelete);

            return "unset";
        }
//      List categoryId is existing
        Set<Long> existingCategoryIds = existingRelations.stream()
                .map(CategoryAttribute::getCategoryId)
                .collect(Collectors.toSet());

        List<CategoryAttribute> relationsToSave = desiredCategoryIds.stream()
                .filter(categoryId -> !existingCategoryIds.contains(categoryId))
                .map(categoryId -> {
                    CategoryAttribute newRelation = new CategoryAttribute();
                    newRelation.setAttributeId(attributeId);
                    newRelation.setCategoryId(categoryId);
                    return newRelation;
                })
                .collect(Collectors.toList());

        if (!relationsToSave.isEmpty()) {
            categoryAttributeRepository.saveAll(relationsToSave);
        }

        return "add";
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteCategoryAttribute(long categoryId, long attributeId) {
        categoryService.checkExistsById(categoryId);

        if (!categoryAttributeRepository.existsByCategoryIdAndAttributeId(categoryId, attributeId)) {
            throw new BusinessCustomException(Constants.Common.global, "Thuộc tính chưa được gắn với danh mục này");
        }

        categoryAttributeRepository.deleteByCategoryIdAndAttributeId(categoryId, attributeId);
    }

    @Override
    public List<CategoryIdNameDto> getAllCategoryIdNameByAttributeId(long attributeId) {
        return categoryAttributeRepository.getAllCategoryIdNameByAttributeId(attributeId);
    }

    @Override
    public List<AttributeIdCategoryIdName> findAttributeIdMapCategoryByAttributeIds(List<Long> attributeIds) {
        return categoryAttributeRepository.findAllAttributeIdMapCategoryIdNameByAttributeIds(attributeIds);
    }


}
