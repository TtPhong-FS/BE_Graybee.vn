package vn.graybee.modules.catalog.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CategoryRequest {

    @NotBlank(message = "category.name.not_blank")
    @Size(min = 2, max = 35, message = "category.name.size")
    private String name;

    @NotBlank(message = "common.status.not_blank")
    private String status;

    @Size(max = 35, message = "category.parentName.size")
    private String parentName;

    @Valid
    private List<AttributeRequest> attributes;


}
