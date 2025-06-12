package vn.graybee.modules.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AttributeRequest {

    private List<String> categoryNames;

    @NotBlank(message = "catalog.validation.attribute.name.not.blank")
    @Size(min = 2, max = 100, message = "catalog.validation.attribute.name.size")
    private String name;

    @Size(min = 2, max = 100, message = "catalog.validation.attribute.label.size")
    private String label;

    private boolean required;

    private boolean active;

    private List<String> options;

    @NotBlank(message = "catalog.validation.attribute.inputType.not.blank")
    @Size(min = 2, max = 30, message = "catalog.validation.attribute.inputType.size")
    private String inputType;

    @Size(max = 30, message = "catalog.validation.attribute.unit.size")
    private String unit;


}
