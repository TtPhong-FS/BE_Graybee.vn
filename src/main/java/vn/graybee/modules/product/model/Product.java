package vn.graybee.modules.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.common.model.BaseModel;
import vn.graybee.modules.product.enums.ProductStatus;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "brand_id", nullable = false)
    private Long brandId;

    @Column(unique = true, nullable = false, length = 200)
    private String slug;

    @Column(unique = true, nullable = false, length = 200)
    private String name;

    @Column(name = "conditions", length = 5, nullable = false)
    private String conditions;

    @Column(name = "warranty", nullable = false)
    private int warranty;

    private double price;

    @Column(name = "discount_percent")
    private int discountPercent;

    @Column(name = "final_price")
    private double finalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Column(length = 250)
    private String thumbnail;

    public double calculateFinalPrice(double price, int discountPercent) {
        return price - (price * discountPercent / 100);
    }

}
