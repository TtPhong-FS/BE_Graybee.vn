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
public class ProductUpdateRequest {

    @NotBlank(message = "product.validation.brandName.not.blank")
    @Size(max = 35, message = "product.validation.brandName.size")
    private String brandName;

    @Size(max = 100)
    private List<@Size(max = 100, message = "product.validation.tagName.size") String> tagNames;

    private String slug;

    @NotBlank(message = "product.validation.name.not.blank")
    @Size(min = 5, max = 300, message = "product.validation.name.size")
    private String name;

    @NotNull(message = "product.validation.conditions.not.null")
    private String conditions;

    @NotNull(message = "product.validation.warranty.not.null")
    @PositiveOrZero(message = "product.validation.warranty.not.negative")
    private int warranty;

    @PositiveOrZero(message = "product.validation.weight.not.negative")
    private float weight;

    @Size(max = 50, message = "product.validation.dimension.size")
    private String dimension;

    @NotNull(message = "product.validation.price.not.null")
    @PositiveOrZero(message = "product.validation.price.not.negative")
    @DecimalMax(value = "100000000.0", message = "product.validation.price.max")
    private BigDecimal price;

    @NotNull(message = "product.validation.discountPercent.not.null")
    @PositiveOrZero(message = "product.validation.discountPercent.not.negative")
    private int discountPercent;

    @Size(max = 35, message = "product.validation.color.size")
    private String color;

    private boolean isStock;

    private boolean hasPromotion;

    @NotNull(message = "product.validation.quantity.not.null")
    @PositiveOrZero(message = "product.validation.quantity.not.negative")
    private int quantity;

    @NotBlank(message = "common.status.not.blank")
    private String status;

    private String description;

    private List<String> images;

    @NotNull(message = "Thông tin chi tiết sản phẩm không được để trống")
    private Map<String, String> attributes;

}
