package vn.graybee.modules.catalog.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.exception.MultipleException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.common.utils.TextUtils;
import vn.graybee.modules.catalog.dto.request.AttributeRequest;
import vn.graybee.modules.catalog.dto.request.AttributesRequest;
import vn.graybee.modules.catalog.dto.request.MultipleAttributeRequest;
import vn.graybee.modules.catalog.dto.response.CategorySummaryDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDto;
import vn.graybee.modules.catalog.enums.InputType;
import vn.graybee.modules.catalog.model.Attribute;
import vn.graybee.modules.catalog.repository.AttributeRepository;
import vn.graybee.modules.catalog.service.AttributeService;
import vn.graybee.modules.catalog.service.CategoryService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AttributeServiceImpl implements AttributeService {

    private final Logger logger = LoggerFactory.getLogger(AttributeServiceImpl.class);

    private final CategoryService categoryService;

    private final AttributeRepository attributeRepository;

    private final MessageSourceUtil messageSourceUtil;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AttributeDto createAttribute(AttributeRequest request) {

        CategorySummaryDto categorySummaryDto = categoryService.getCategorySummaryByNameOrId(request.getCategoryName(), null);

        checkExistsByName(request.getName(), categorySummaryDto.getId());

        Attribute attribute = new Attribute();
        attribute.setCategoryId(categorySummaryDto.getId());
        attribute.setName(convertName(request.getName()));
        attribute.setLabel(TextUtils.capitalizeEachWord(request.getLabel()));
        attribute.setUnit(request.getUnit().toUpperCase());
        attribute.setInputType(getInputType(request.getInputType()));
        attribute.setRequired(request.isRequired());
        attribute.setOptions(request.getOptions());

        attributeRepository.save(attribute);

        return new AttributeDto(attribute, categorySummaryDto.getName());
    }

    private AttributeDto toDto(Attribute attribute, String categoryName) {
        return new AttributeDto(
                attribute,
                categoryName
        );
    }

    private String getInputType(String type) {
        return InputType.getType(type, messageSourceUtil);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<AttributeDto> createMultipleAttribute(MultipleAttributeRequest request) {

        CategorySummaryDto categorySummaryDto = categoryService.getCategorySummaryByNameOrId(request.getCategoryName(), null);

        if (request.getAttributes() == null || request.getAttributes().isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> existingNames = attributeRepository
                .findAllNamesByCategoryId(categorySummaryDto.getId())
                .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        checkExistsNameWithCategoryId(existingNames, request.getAttributes());

        List<Attribute> attributes = request.getAttributes().stream()
                .map(attr -> {
                    Attribute attribute = new Attribute();
                    attribute.setCategoryId(categorySummaryDto.getId());
                    attribute.setName(convertName(attr.getName()));
                    attribute.setLabel(TextUtils.capitalizeEachWord(attr.getLabel()));
                    attribute.setUnit(attr.getUnit() != null && !attr.getUnit().isEmpty() ? attr.getUnit().toUpperCase() : null);
                    attribute.setInputType(getInputType(attr.getInputType()));
                    attribute.setRequired(attr.isRequired());
                    attribute.setOptions(attr.getOptions());
                    return attribute;
                })
                .collect(Collectors.toList());

        attributeRepository.saveAll(attributes);

        return attributes.stream().map(a -> toDto(a, categorySummaryDto.getName())).toList();

    }

    private String convertName(String name) {
        return name
                .toLowerCase()
                .replaceAll("[ ]+", "_")
                .replaceAll("^_+|_+$", "");
    }

    private void checkExistsNameWithCategoryId(Set<String> existingNames, List<AttributesRequest> attributesRequests) {

        if (existingNames.isEmpty()) {
            return;
        }

        Map<String, String> errors = new HashMap<>();

        for (int i = 0; i < attributesRequests.size(); i++) {
            AttributesRequest attr = attributesRequests.get(i);

            if (existingNames.contains(convertName(attr.getName()))) {
                errors.put("attributes[" + i + "].name", attr.getName() + " name already exists");
            }
        }

        if (!errors.isEmpty()) {
            throw new MultipleException(errors);
        }
    }

    public void checkExistsByName(String name, long categoryId) {
        if (attributeRepository.existsByNameAndCategoryId(name, categoryId)) {
            throw new BusinessCustomException(Constants.Common.name, messageSourceUtil.get("catalog.attribute.name.exists.with.category", new Object[]{name}));
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AttributeDto updateAttribute(Long id, AttributeRequest request) {

        CategorySummaryDto categorySummaryDto = categoryService.getCategorySummaryByNameOrId(request.getCategoryName(), null);

        checkExistsByName(request.getName(), categorySummaryDto.getId());

        Attribute attribute = attributeRepository.findById(id)
                .orElseGet(Attribute::new);

        attribute.setCategoryId(categorySummaryDto.getId());
        attribute.setName(convertName(request.getName()));
        attribute.setLabel(TextUtils.capitalizeEachWord(request.getLabel()));
        attribute.setUnit(request.getUnit().toUpperCase());
        attribute.setInputType(getInputType(request.getInputType()));
        attribute.setRequired(request.isRequired());
        attribute.setOptions(request.getOptions());

        return new AttributeDto(attribute, categorySummaryDto.getName());
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
        return attributeRepository.findAttributeDtoById(id).orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("catalog.attribute.not.found")));
    }

    @Override
    public List<AttributeDto> findAllAttributeDto() {
        return attributeRepository.findAllAttributeDto();
    }

    @Override
    public List<AttributeBasicDto> findAllAttributeBasicDtoByCategoryId(Long categoryId) {
        return attributeRepository.findAllAttributeBasicDtoByCategoryId(categoryId);
    }

}
