package vn.graybee.modules.product.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDisplayDto;
import vn.graybee.modules.comment.dto.response.ReviewCommentDto;
import vn.graybee.modules.product.model.Product;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ProductDetailDto {

    private long productId;

    private String name;

    private String slug;

    private String brandName;

    private int warranty;

    private String conditions;

    private float weight;

    private String color;

    private String thumbnail;

    private List<String> images;

    private BigDecimal price;

    private BigDecimal finalPrice;

    private int discountPercent;

    private String description;

    private List<ReviewCommentDto> reviews;

    private List<AttributeDisplayDto> details;

    public ProductDetailDto(Product product, String description) {
        this.productId = product.getId();
        this.name = product.getName();
        this.slug = product.getSlug();
        this.warranty = product.getWarranty();
        this.conditions = product.getConditions();
        this.weight = product.getWeight();
        this.color = product.getColor();
        this.thumbnail = product.getThumbnail();
        this.price = product.getPrice();
        this.finalPrice = product.getFinalPrice();
        this.discountPercent = product.getDiscountPercent();
        this.description = description;
    }

}
