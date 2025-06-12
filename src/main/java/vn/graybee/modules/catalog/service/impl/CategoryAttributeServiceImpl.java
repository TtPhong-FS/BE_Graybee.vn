package vn.graybee.modules.catalog.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createCategoryAttribute(List<String> categoryNames, long attributeId) {

        if (categoryNames.isEmpty()) {
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
    public void updateCategoryAttribute(List<String> categoryNames, long attributeId) {
        if (categoryNames.isEmpty()) {
            categoryAttributeRepository.deleteByAttributeId(attributeId);
            return;
        }

        List<CategoryIdNameDto> categoryIdNameDtos = categoryService.getCategoryIdNameDtos(categoryNames);

//        List categoryId to new relation
        Set<Long> desiredCategoryIds = categoryIdNameDtos.stream()
                .map(CategoryIdNameDto::getId)
                .collect(Collectors.toSet());

        List<CategoryAttribute> existingRelations = categoryAttributeRepository.findAllByAttributeId(attributeId);

//        Fill relation don't need in new relation
        List<CategoryAttribute> relationsToDelete = existingRelations.stream()
                .filter(relation -> !desiredCategoryIds.contains(relation.getCategoryId()))
                .collect(Collectors.toList());

        if (!relationsToDelete.isEmpty()) {
            categoryAttributeRepository.deleteAll(relationsToDelete);
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
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteCategoryAttribute(long categoryId, long attributeId) {
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
