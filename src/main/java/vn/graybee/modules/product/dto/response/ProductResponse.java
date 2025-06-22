package vn.graybee.modules.product.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.product.enums.ProductStatus;
import vn.graybee.modules.product.model.Product;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class ProductResponse {

    private long id;

    private String categoryName;

    private String brandName;

    private List<String> tagNames;

    private String slug;

    private String name;

    private int warranty;

    private double price;

    private int discountPercent;

    private double finalPrice;

    private String thumbnail;

    private String conditions;

    private ProductStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public ProductResponse(Product product, String categoryName, String brandName, List<String> tagNames) {
        this.id = product.getId();
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.tagNames = tagNames;
        this.slug = product.getSlug();
        this.name = product.getName();
        this.warranty = product.getWarranty();
        this.price = product.getPrice();
        this.discountPercent = product.getDiscountPercent();
        this.finalPrice = product.getFinalPrice();
        this.thumbnail = product.getThumbnail();
        this.conditions = product.getConditions();
        this.status = product.getStatus();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }

}
