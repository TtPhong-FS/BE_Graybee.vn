package vn.graybee.modules.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BrandRequest {

    @NotBlank(message = "catalog.validation.brand.name.not.blank")
    @Size(min = 2, max = 35, message = "catalog.validation.brand.name.size")
    private String name;

    @Size(max = 35, message = "catalog.validation.brand.slug.name.size")
    private String slug;

    @NotBlank(message = "common.status.not_blank")
    private String status;

}
