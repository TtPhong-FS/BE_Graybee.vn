package vn.graybee.modules.catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.catalog.enums.CategoryStatus;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class CategoryUpdateDto {

    private String name;

    private String parentName;

    private String slug;

    private String type;

    private CategoryStatus status;


}
