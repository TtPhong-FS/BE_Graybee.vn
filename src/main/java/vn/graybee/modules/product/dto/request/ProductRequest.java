package vn.graybee.modules.product.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ProductRequest {

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

    @NotNull(message = "product.validation.conditions.not.null")
    private String conditions;

    @NotNull(message = "product.validation.warranty.not.null")
    @PositiveOrZero(message = "product.validation.warranty.not.negative")
    private int warranty;

    @NotNull(message = "product.validation.price.not.null")
    @PositiveOrZero(message = "product.validation.price.not.negative")
    @DecimalMax(value = "100000000.0", message = "product.validation.price.max")
    private BigDecimal price;

    @NotNull(message = "product.validation.discountPercent.not.null")
    @PositiveOrZero(message = "product.validation.discountPercent.not.negative")
    private int discountPercent;

    @NotNull(message = "product.validation.quantity.not.null")
    @PositiveOrZero(message = "product.validation.quantity.not.negative")
    private int quantity;

    private String description;

    private List<String> images;

    private Map<String, String> specifications;

}
