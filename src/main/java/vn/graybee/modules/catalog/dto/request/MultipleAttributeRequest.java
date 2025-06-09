package vn.graybee.modules.catalog.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MultipleAttributeRequest {

    @NotBlank(message = "catalog.validation.attribute.categoryName.not.blank")
    @Size(min = 2, max = 35, message = "catalog.validation.attribute.categoryName.size")
    private String categoryName;

    @Valid
    private List<AttributesRequest> attributes;

}
