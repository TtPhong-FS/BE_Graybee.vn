package vn.graybee.modules.product.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class ProductCreateRequest {

    @NotBlank(message = "{product.validation.productType.not_blank}")
    @Size(max = 35, message = "{product.validation.productType.size}")
    private String productType;

    @NotBlank(message = "{product.validation.brand.not_blank}")
    @Size(max = 35, message = "{product.validation.brand.size}")
    private String brand;

    private List<String> usages;

    private List<String> techSpecs;

    private List<String> features;

    private List<String> priceRanges;

    @NotBlank(message = "{product.validation.name.not_blank}")
    @Size(min = 5, max = 300, message = "{product.validation.name.size}")
    private String name;

    @NotNull(message = "{product.validation.conditions.not_null}")
    private String conditions;

    @NotNull(message = "{product.validation.warranty.not_null}")
    @PositiveOrZero(message = "{product.validation.quantity.not_negative}")
    private int warranty;

    @PositiveOrZero(message = "{product.validation.weight.not_negative}")
    private float weight;

    @Size(max = 50, message = "{product.validation.dimension.size}")
    private String dimension;

    @NotNull(message = "{product.validation.price.not_null}")
    @PositiveOrZero(message = "{product.validation.price.not_negative}")
    @DecimalMax(value = "100000000.0", message = "{product.validation.price.max}")
    private BigDecimal price;

    @NotNull(message = "{product.validation.discountPercent.not_null}")
    @PositiveOrZero(message = "{product.validation.discountPercent.not_negative}")
    private int discountPercent;

    @Size(max = 35, message = "{product.validation.color.size}")
    private String color;

    private boolean isStock;

    private boolean hasPromotion;

    @NotNull(message = "{product.validation.quantity.not_null}")
    @PositiveOrZero(message = "{product.validation.quantity.not_negative}")
    private Integer quantity;

    @NotBlank(message = "{common.status.not_blank}")
    private String status;

    private String description;

    private List<String> images;

    @Valid
    private DetailTemplateRequest detail;

}
