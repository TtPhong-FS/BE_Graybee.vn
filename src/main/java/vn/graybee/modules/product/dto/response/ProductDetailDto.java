package vn.graybee.modules.product.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDisplayDto;
import vn.graybee.modules.comment.dto.response.ReviewCommentDto;
import vn.graybee.modules.product.model.Product;

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

    private String thumbnail;

    private double price;

    private double finalPrice;

    private int discountPercent;

    private String description;

    private List<ReviewCommentDto> reviews;

    private List<AttributeDisplayDto> specifications;

    public ProductDetailDto(Product product, String description) {
        this.productId = product.getId();
        this.name = product.getName();
        this.slug = product.getSlug();
        this.warranty = product.getWarranty();
        this.conditions = product.getConditions();
        this.thumbnail = product.getThumbnail();
        this.price = product.getPrice();
        this.finalPrice = product.getFinalPrice();
        this.discountPercent = product.getDiscountPercent();
        this.description = description;
    }

}
