package vn.graybee.modules.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttributeRequest {

    @NotBlank(message = "{category.attribute.name.not_blank}")
    @Size(min = 2, max = 100, message = "{category.attribute.name.size}")
    private String name;

    @NotBlank(message = "{category.attribute.inputType.not_blank}")
    @Size(min = 2, max = 30, message = "{category.attribute.inputType.size}")
    private String inputType;

    @Size(max = 30, message = "{category.attribute.unit.size}")
    private String unit;

}
