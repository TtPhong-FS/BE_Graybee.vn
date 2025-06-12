package vn.graybee.modules.catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.catalog.enums.CategoryType;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class CategoryUpdateDto {

    private Long id;

    private String name;

    private String parentName;

    private String slug;

    private CategoryType type;

    private boolean isActive;


}
