package vn.graybee.modules.product.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.exception.MultipleException;
import vn.graybee.modules.catalog.dto.response.CategoryBasicDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicValueDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDisplayDto;
import vn.graybee.modules.catalog.service.AttributeService;
import vn.graybee.modules.catalog.service.CategoryService;
import vn.graybee.modules.product.model.ProductAttributeValue;
import vn.graybee.modules.product.repository.ProductAttributeValueRepository;
import vn.graybee.modules.product.service.ProductAttributeValueService;
import vn.graybee.modules.product.service.ProductCategoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductAttributeValueServiceImpl implements ProductAttributeValueService {

    private final ProductAttributeValueRepository productAttributeValueRepository;

    private final CategoryService categoryService;

    private final AttributeService attributeService;

    private final ProductCategoryService productCategoryService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createProductAttributeValue(long productId, long categoryId, String categoryName, Map<String, String> attributes) {

        List<AttributeBasicDto> attributeBasicDtos = attributeService.findAllAttributeBasicDtoByCategoryId(categoryId);

        Map<String, AttributeBasicDto> attributeMap = attributeBasicDtos.stream()
                .collect(Collectors.toMap(AttributeBasicDto::getName, Function.identity()));

        List<ProductAttributeValue> productAttributeValues = new ArrayList<>();

        checkAttributeExistsWithCategoryAfterCheck(attributeMap, categoryName, attributes);

        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();

            AttributeBasicDto attribute = attributeMap.get(name);

            ProductAttributeValue pav = new ProductAttributeValue();
            pav.setProductId(productId);
            pav.setAttributeId(attribute.getId());
            pav.setValue(value);

            productAttributeValues.add(pav);
        }

        productAttributeValueRepository.saveAll(productAttributeValues);
    }

    @Override
    public void updateProductAttributeValue(long productId, Map<String, String> attributes) {

        CategoryBasicDto categoryBasicDto = productCategoryService.findCategoryBasicDtoByProductId(productId);

        List<AttributeBasicDto> attributeBasicDtos = attributeService.findAllAttributeBasicDtoByCategoryId(categoryBasicDto.getId());

        Map<String, AttributeBasicDto> attributeMap = attributeBasicDtos.stream()
                .collect(Collectors.toMap(AttributeBasicDto::getName, Function.identity()));

        checkAttributeExistsWithCategoryAfterCheck(attributeMap, categoryBasicDto.getName(), attributes);

        List<ProductAttributeValue> existingValues = productAttributeValueRepository.findAllByProductId(productId);

        Map<Long, ProductAttributeValue> existingMap = existingValues.stream()
                .collect(Collectors.toMap(ProductAttributeValue::getAttributeId, Function.identity()));

        List<ProductAttributeValue> valuesToSave = new ArrayList<>();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();

            AttributeBasicDto attribute = attributeMap.get(name);
            Long attributeId = attribute.getId();

            ProductAttributeValue pav = existingMap.get(attributeId);
            if (pav == null) {
                pav = new ProductAttributeValue();
                pav.setProductId(productId);
                pav.setAttributeId(attributeId);
            }

            pav.setValue(value);
            valuesToSave.add(pav);
        }

        productAttributeValueRepository.saveAll(valuesToSave);
    }

    @Override
    public List<AttributeDisplayDto> getAttributeDisplayDtosByProductId(long productId) {
        return productAttributeValueRepository.findAttributeDisplayDtosByProductId(productId);
    }

    @Override
    public List<AttributeBasicValueDto> getAllAttributeValueByCategoryAndProduct(long categoryId, long productId) {
        return productAttributeValueRepository.AllAttributeValueByCategoryAndProductId(categoryId, productId);
    }

    private void checkAttributeExistsWithCategoryAfterCheck(
            Map<String, AttributeBasicDto> attributeMap,
            String categoryName,
            Map<String, String> attributes) {

        Map<String, String> errors = new HashMap<>();


        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String name = entry.getKey().toLowerCase();
            String value = entry.getValue();

            AttributeBasicDto attribute = attributeMap.get(name);

            if (attribute == null) {
                errors.put("attributes." + name,
                        "Attribute " + name + " is not allowed for category " + categoryName);
            } else {
                checkInputTypeAndRequired(errors, name, value, attribute);
            }
        }

        if (!errors.isEmpty()) {
            throw new MultipleException(errors);
        }
    }

    private void checkInputTypeAndRequired(
            Map<String, String> errors,
            String attrName,
            String rawValue,
            AttributeBasicDto attributeBasicDto) {
        String fieldPath = "attributes." + attrName;

        // Check required
        if (attributeBasicDto.isRequired() && (rawValue == null || rawValue.trim().isEmpty())) {
            errors.put(fieldPath, "Attribute '" + attrName + "' is required");
        }

        if (rawValue == null || rawValue.trim().isEmpty()) {
            return;
        }

        String inputType = attributeBasicDto.getInputType().toLowerCase();

        switch (inputType) {
            case "checkbox":
                if (!rawValue.equals("true") && !rawValue.equals("false")) {
                    errors.put(fieldPath, "Checkbox attribute '" + attrName + "' must be true or false");
                }
                break;

            case "select":
                List<String> options = attributeBasicDto.getOptions();
                if (!options.contains(rawValue)) {
                    errors.put(fieldPath, "Invalid option for '" + attrName + "', allowed values: " + options);
                }
                break;

            default:
                break;
        }
    }

}
