package vn.graybee.modules.product.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import vn.graybee.modules.product.model.Product;
import vn.graybee.response.admin.products.StatusResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ProductResponse {

    private long id;

    private List<String> categoryNames;

    private String name;

    private int warranty;

    private float weight;

    private String dimension;

    private BigDecimal price;

    private int discountPercent;

    private BigDecimal finalPrice;

    private String color;

    private String thumbnail;

    private String conditions;

    private StatusResponse status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.warranty = product.getWarranty();
        this.weight = product.getWeight();
        this.dimension = product.getDimension();
        this.price = product.getPrice();
        this.discountPercent = product.getDiscountPercent();
        this.finalPrice = product.getFinalPrice();
        this.color = product.getColor();
        this.thumbnail = product.getThumbnail();
        this.conditions = product.getConditions();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.status = new StatusResponse(product.getStatus().getCode(), product.getStatus().getDisplayName());
    }

}
