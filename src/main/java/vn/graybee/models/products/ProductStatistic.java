package vn.graybee.models.products;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import vn.graybee.models.BaseModel;

@Entity
@Table(name = "product_statistics")
public class ProductStatistic extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "purchase_count")
    private int purchaseCount;

    @Column(name = "has_promotion")
    private boolean hasPromotion;

    public ProductStatistic() {
    }

    public void decreaseViewCount() {
        this.viewCount++;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public boolean isHasPromotion() {
        return hasPromotion;
    }

    public void setHasPromotion(boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
    }

}
