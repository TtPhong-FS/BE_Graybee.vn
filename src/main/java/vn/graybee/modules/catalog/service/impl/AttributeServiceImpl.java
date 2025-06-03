package vn.graybee.modules.catalog.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.catalog.dto.request.AttributeRequest;
import vn.graybee.modules.catalog.dto.response.CategoryAttributesDto;
import vn.graybee.modules.catalog.model.Attribute;
import vn.graybee.modules.catalog.repository.AttributeRepository;
import vn.graybee.modules.catalog.service.AttributeService;
import vn.graybee.modules.catalog.service.CategoryAttributeService;

import java.util.List;

@AllArgsConstructor
@Service
public class AttributeServiceImpl implements AttributeService {

    private final Logger logger = LoggerFactory.getLogger(AttributeServiceImpl.class);

    private final AttributeRepository attributeRepository;

    private final CategoryAttributeService categoryAttributeService;

    @Override
    public BasicMessageResponse<CategoryAttributesDto> saveAttributesByCategoryId(Long categoryId, List<AttributeRequest> requests) {

        for (AttributeRequest request : requests) {
            Attribute attribute = attributeRepository.findByName(request.getName())
                    .orElseGet(() -> {
                        Attribute newAttribute = new Attribute();
                        newAttribute.setName(request.getName());
                        newAttribute.setInputType(request.getInputType());
                        newAttribute.setUnit(request.getUnit());

                        return attributeRepository.save(newAttribute);
                    });

            categoryAttributeService.saveByCategoryIdAndAttributeId(categoryId, attribute.getId());
            logger.info("Attribute created with ID: {}", attribute.getId());
        }

        return null;

    }

}
