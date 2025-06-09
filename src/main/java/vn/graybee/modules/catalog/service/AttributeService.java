package vn.graybee.modules.catalog.service;

import vn.graybee.modules.catalog.dto.request.AttributeRequest;
import vn.graybee.modules.catalog.dto.request.MultipleAttributeRequest;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDto;

import java.util.List;

public interface AttributeService {

    AttributeDto createAttribute(AttributeRequest request);

    List<AttributeDto> createMultipleAttribute(MultipleAttributeRequest request);

    AttributeDto updateAttribute(Long id, AttributeRequest request);

    Long deleteById(Long id);

    AttributeDto findById(Long id);

    List<AttributeDto> findAllAttributeDto();

    List<AttributeBasicDto> findAllAttributeBasicDtoByCategoryId(Long categoryId);

}
