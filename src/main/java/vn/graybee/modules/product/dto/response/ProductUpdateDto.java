package vn.graybee.modules.product.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeBasicValueDto;
import vn.graybee.modules.product.enums.ProductStatus;
import vn.graybee.modules.product.model.Product;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class ProductUpdateDto {

    private long id;

    private String categoryName;

    private String brandName;

    private List<String> tagNames;

    private String name;

    private String slug;

    private int warranty;

    private BigDecimal price;

    private int discountPercent;

    private BigDecimal finalPrice;

    private List<String> images;

    private String thumbnail;

    private String conditions;

    private int quantity;

    private String description;

    private ProductStatus status;

    private List<AttributeBasicValueDto> specifications;

    public ProductUpdateDto(Product product, String categoryName, String brandName, int quantity, String description) {
        this.id = product.getId();
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.name = product.getName();
        this.slug = product.getSlug();
        this.warranty = product.getWarranty();
        this.price = product.getPrice();
        this.discountPercent = product.getDiscountPercent();
        this.finalPrice = product.getFinalPrice();
        this.thumbnail = product.getThumbnail();
        this.conditions = product.getConditions();
        this.status = product.getStatus();
        this.quantity = quantity;
        this.description = description;
    }

}
