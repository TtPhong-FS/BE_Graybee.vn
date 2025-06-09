package vn.graybee.modules.product.service;

import vn.graybee.modules.catalog.dto.response.attribute.AttributeDisplayDto;

import java.util.List;
import java.util.Map;

public interface ProductAttributeValueService {

    void createProductAttributeValue(long productId, String categoryName, Map<String, String> attributes);

    void updateProductAttributeValue(long productId, Map<String, String> attributes);

    List<AttributeDisplayDto> getAttributeDisplayDtosByProductId(long productId);

}
