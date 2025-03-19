package vn.graybee.models.products;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import vn.graybee.models.others.BaseModel;

@Entity
@Table(name = "products_tags", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "tag_id"}))
public class ProductTag extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "tag_id", nullable = false)
    private Integer tagId;

    @Column(length = 30, nullable = false)
    private String status;

    public ProductTag() {
    }

    public ProductTag(Long productId, Integer tagId, String status) {
        this.productId = productId;
        this.tagId = tagId;
        this.status = status;
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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
