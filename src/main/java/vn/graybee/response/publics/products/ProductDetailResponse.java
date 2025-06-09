package vn.graybee.response.publics.products;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.comment.dto.response.ReviewCommentDto;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ProductDetailResponse {

    private long id;

    private String name;

    private String categoryName;

    private String manufacturerName;

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

    public ProductDetailResponse(long id, String name, int warranty, String conditions, float weight, String color, String thumbnail, BigDecimal price, BigDecimal finalPrice, int discountPercent) {
        this.id = id;
        this.name = name;
        this.warranty = warranty;
        this.conditions = conditions;
        this.weight = weight;
        this.color = color;
        this.thumbnail = thumbnail;
        this.price = price;
        this.finalPrice = finalPrice;
        this.discountPercent = discountPercent;
    }


}
