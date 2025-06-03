package vn.graybee.modules.catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.catalog.enums.CategoryStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategorySummaryDto {

    private Long id;

    private String name;

    private CategoryStatus status;

}
