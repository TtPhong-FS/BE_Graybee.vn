package vn.graybee.modules.catalog.service;

import vn.graybee.modules.catalog.dto.response.CategoryIdNameDto;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeIdCategoryIdName;

import java.util.List;

public interface CategoryAttributeService {

    void createCategoryAttribute(List<String> categoryNames, long attributeId);

    String updateCategoryAttribute(List<CategoryIdNameDto> incommingCategory, List<String> categoryNames, long attributeId);

    void deleteCategoryAttribute(long categoryId, long attributeId);

    List<CategoryIdNameDto> getAllCategoryIdNameByAttributeId(long attributeId);

    List<AttributeIdCategoryIdName> findAttributeIdMapCategoryByAttributeIds(List<Long> attributeIds);


}
