package vn.graybee.modules.catalog.service;

import vn.graybee.modules.catalog.dto.request.AttributeRequest;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeIdActiveResponse;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeIdAllCategoryIdName;

import java.util.List;

public interface AttributeService {

    AttributeDto createAttribute(AttributeRequest request);

    AttributeDto updateAttribute(Long id, AttributeRequest request);

    Long deleteById(Long id);

    AttributeDto findById(Long id);

    List<AttributeDto> findAllAttributeDto();

    List<AttributeBasicDto> findAllAttributeBasicDtoByCategoryId(long categoryId);

    AttributeIdActiveResponse setShowOrHideById(long id);

    AttributeIdAllCategoryIdName addCategoriesToAttributeById(long id, List<String> categoryNames);

}
