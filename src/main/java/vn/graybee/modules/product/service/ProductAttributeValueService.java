package vn.graybee.modules.product.service;

import vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicValueDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDisplayDto;

import java.util.List;
import java.util.Map;

public interface ProductAttributeValueService {

    void createProductAttributeValue(long productId, long categoryId, String categoryName, Map<String, String> attributes);

    void updateProductAttributeValue(long productId, Map<String, String> attributes);

    List<AttributeDisplayDto> getAttributeDisplayDtosByProductId(long productId);

    List<AttributeBasicValueDto> getAllAttributeValueByCategoryAndProduct(long categoryId, long productId);

}
