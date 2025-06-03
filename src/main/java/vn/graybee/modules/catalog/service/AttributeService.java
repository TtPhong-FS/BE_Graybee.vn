package vn.graybee.modules.catalog.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.catalog.dto.request.AttributeRequest;
import vn.graybee.modules.catalog.dto.response.CategoryAttributesDto;

import java.util.List;

public interface AttributeService {

    BasicMessageResponse<CategoryAttributesDto> saveAttributesByCategoryId(Long categoryId, List<AttributeRequest> requests);

}
