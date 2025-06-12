package vn.graybee.modules.catalog.dto.response.attribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.catalog.dto.response.CategoryIdNameDto;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttributeIdAllCategoryIdName {

    private Long id;

    private List<CategoryIdNameDto> categories;

}
