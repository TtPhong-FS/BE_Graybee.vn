package vn.graybee.modules.catalog.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.common.utils.TextUtils;
import vn.graybee.modules.catalog.dto.request.AttributeRequest;
import vn.graybee.modules.catalog.dto.response.CategoryIdNameDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeIdActiveResponse;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeIdAllCategoryIdName;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeIdCategoryId;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeIdCategoryIdName;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeIdRequiredResponse;
import vn.graybee.modules.catalog.enums.InputType;
import vn.graybee.modules.catalog.model.Attribute;
import vn.graybee.modules.catalog.repository.AttributeRepository;
import vn.graybee.modules.catalog.service.AttributeService;
import vn.graybee.modules.catalog.service.CategoryAttributeService;
import vn.graybee.modules.catalog.service.CategoryService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AttributeServiceImpl implements AttributeService {

    private final Logger logger = LoggerFactory.getLogger(AttributeServiceImpl.class);

    private final CategoryService categoryService;

    private final CategoryAttributeService categoryAttributeService;

    private final AttributeRepository attributeRepository;

    private final MessageSourceUtil messageSourceUtil;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AttributeDto createAttribute(AttributeRequest request) {

        checkExistsByName(request.getName());

        Attribute attribute = new Attribute();
        attribute.setName(convertName(request.getName()));
        attribute.setLabel(TextUtils.capitalizeEachWord(request.getLabel()));
        attribute.setUnit(request.getUnit() != null && !request.getUnit().isEmpty() ? request.getUnit().toUpperCase() : null);
        attribute.setInputType(getInputType(request.getInputType()));
        attribute.setRequired(request.isRequired());
        attribute.setActive(request.isActive());
        attribute.setOptions(request.getOptions());

        attribute = attributeRepository.save(attribute);

        categoryAttributeService.createCategoryAttribute(request.getCategoryNames(), attribute.getId());

        List<CategoryIdNameDto> categories = categoryService.getCategoryIdNameDtos(request.getCategoryNames());

        return toDto(attribute, categories);
    }

    private AttributeDto toDto(Attribute attribute, List<CategoryIdNameDto> categories) {
        return new AttributeDto(
                attribute,
                categories
        );
    }

    private String getInputType(String type) {
        return InputType.getType(type, messageSourceUtil);
    }

    private String convertName(String name) {
        return name
                .toLowerCase()
                .replaceAll("[ ]+", "_")
                .replaceAll("^_+|_+$", "");
    }

    public void checkExistsByName(String name) {
        if (attributeRepository.existsByName(name)) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("catalog.attribute.name.exists", new Object[]{name}));
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AttributeDto updateAttribute(Long id, AttributeRequest request) {

        Attribute attribute = attributeRepository.findById(id)
                .orElseGet(Attribute::new);

        if (attribute.getId() != null) {
            if (attributeRepository.checkExistsByNameNotId(request.getName(), attribute.getId())) {
                throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("catalog.attribute.name.exists"));
            }
        }

        attribute.setName(convertName(request.getName()));
        attribute.setLabel(TextUtils.capitalizeEachWord(request.getLabel()));
        attribute.setUnit(request.getUnit() != null && !request.getUnit().isEmpty() ? request.getUnit().toUpperCase() : null);
        attribute.setInputType(getInputType(request.getInputType()));
        attribute.setRequired(request.isRequired());
        attribute.setActive(request.isActive());
        attribute.setOptions(request.getOptions());

        attribute = attributeRepository.save(attribute);

        List<CategoryIdNameDto> categories = categoryService.getCategoryIdNameDtos(request.getCategoryNames());

        categoryAttributeService.updateCategoryAttribute(categories, request.getCategoryNames(), attribute.getId());

        return toDto(attribute, categories);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Long deleteById(Long id) {

        if (!attributeRepository.existsById(id)) {
            return id;
        }
        attributeRepository.deleteById(id);

        return id;
    }

    @Override
    public AttributeDto findById(Long id) {
        AttributeDto attributeDto = attributeRepository.findAttributeDtoById(id).orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.attribute.not.found")));

        List<CategoryIdNameDto> categoryIdNameDtos = categoryAttributeService.getAllCategoryIdNameByAttributeId(attributeDto.getId());

        attributeDto.setCategories(categoryIdNameDtos);

        return attributeDto;
    }

    @Override
    public List<AttributeDto> findAllAttributeDto() {
        List<AttributeDto> attributeDtos = attributeRepository.findAllAttributeDto();

        List<Long> attributeIds = attributeDtos.stream().map(AttributeDto::getId).toList();

        List<AttributeIdCategoryIdName> attributeIdCategoryIdNames = categoryAttributeService.findAttributeIdMapCategoryByAttributeIds(attributeIds);

        Map<Long, List<CategoryIdNameDto>> attributeMapCategory = attributeIdCategoryIdNames.stream().collect(
                Collectors.groupingBy(AttributeIdCategoryIdName::getAttributeId, Collectors.mapping(a -> new CategoryIdNameDto(a.getCategoryId(), a.getCategoryName()), Collectors.toList()))
        );

        attributeDtos.forEach(
                a -> {
                    List<CategoryIdNameDto> categories = attributeMapCategory.getOrDefault(a.getId(), Collections.emptyList());
                    a.setCategories(categories);
                }
        );

        return attributeDtos;
    }

    @Override
    public List<AttributeBasicDto> findAllAttributeBasicDtoByCategoryId(long categoryId) {
        return attributeRepository.findAllAttributeBasicDtoByCategoryId(categoryId);
    }

    @Override
    public List<AttributeBasicDto> findAllAttributeBasicDtoByCategoryName(String categoryName) {
        return attributeRepository.findAllAttributeBasicDtoByCategoryName(categoryName);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AttributeIdActiveResponse setShowOrHideById(long id) {
        Boolean active = attributeRepository.getActiveById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.attribute.not.found")));

        attributeRepository.toggleActiveById(id);

        return new AttributeIdActiveResponse(id, !active);
    }

    private void checkExistsById(long id) {
        if (!attributeRepository.existsById(id)) {
            throw new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.attribute.not.found"));
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AttributeIdAllCategoryIdName addCategoriesToAttributeById(long id, List<String> categoryNames) {
        checkExistsById(id);

        List<CategoryIdNameDto> categories = categoryService.getCategoryIdNameDtos(categoryNames);

        categoryAttributeService.updateCategoryAttribute(categories, categoryNames, id);

        return new AttributeIdAllCategoryIdName(id, categories);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AttributeIdCategoryId removeCategoryByCategoryIdAndId(long id, long categoryId) {
        checkExistsById(id);

        categoryAttributeService.deleteCategoryAttribute(categoryId, id);

        return new AttributeIdCategoryId(id, categoryId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AttributeIdRequiredResponse toggleRequiredById(long id) {
        Boolean required = attributeRepository.getRequiredById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.attribute.not.found")));

        attributeRepository.toggleRequiredById(id);

        return new AttributeIdRequiredResponse(id, !required);
    }

}
