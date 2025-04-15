package vn.graybee.models.carousels;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "carousel_items", uniqueConstraints = @UniqueConstraint(columnNames = {"carousel_group_id", "product_id"}))
public class CarouselItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "carousel_group_id", nullable = false)
    private Integer carouselGroupId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    private int position;

    private boolean active;

    public CarouselItem() {
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

    public Integer getCarouselGroupId() {
        return carouselGroupId;
    }

    public void setCarouselGroupId(Integer carouselGroupId) {
        this.carouselGroupId = carouselGroupId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
