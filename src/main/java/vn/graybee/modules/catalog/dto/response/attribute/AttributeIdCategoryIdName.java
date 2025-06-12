package vn.graybee.modules.catalog.dto.response.attribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeIdCategoryIdName {

    private Long attributeId;

    private Long categoryId;

    private String categoryName;

}
