package vn.graybee.modules.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequest {

    @NotBlank(message = "catalog.validation.category.name.not.blank")
    @Size(min = 2, max = 100, message = "catalog.validation.category.name.size")
    private String name;

    @Size(max = 100, message = "catalog.validation.category.slug.name.size")
    private String slug;

    @NotBlank(message = "catalog.validation.category.type.not.blank")
    private String type;

    @NotBlank(message = "common.status.not_blank")
    private String status;

    @Size(max = 35, message = "catalog.validation.category.parentName.size")
    private String parentName;

}
