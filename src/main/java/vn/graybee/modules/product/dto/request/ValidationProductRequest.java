package vn.graybee.modules.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ValidationProductRequest {

    @NotBlank(message = "product.validation.name.not.blank")
    @Size(min = 5, max = 200, message = "product.validation.name.size")
    private String name;

    @Size(max = 200, message = "product.validation.slug.max")
    private String slug;

    @NotBlank(message = "product.validation.categoryName.not.blank")
    @Size(max = 50, message = "product.validation.categoryName.size")
    private String categoryName;

    @NotBlank(message = "product.validation.brandName.not.blank")
    @Size(max = 50, message = "product.validation.brandName.size")
    private String brandName;

    @Size(max = 100)
    private List<@Size(max = 100, message = "product.validation.tagName.size") String> tagNames;

    @NotBlank(message = "product.validation.status.not.blank")
    private String status;

}
