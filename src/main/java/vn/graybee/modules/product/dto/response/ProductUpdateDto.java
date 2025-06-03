package vn.graybee.modules.product.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.graybee.modules.product.enums.ProductStatus;
import vn.graybee.modules.product.model.Product;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class ProductUpdateDto {

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

    private List<String> images;

    private String conditions;

    private Boolean isStock;

    private int quantity;

    private String description;

    private ProductStatus status;

    public ProductUpdateDto() {
    }

    public ProductUpdateDto(Product product, String description, int quantity, Boolean isStock) {
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
        this.isStock = isStock;
        this.status = product.getStatus();
        this.description = description;
        this.quantity = quantity;
    }

}
