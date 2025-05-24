package vn.graybee.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "product_classification",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "tag_id"}),
                @UniqueConstraint(columnNames = {"product_id", "subcategory_id"})
        })
public class ProductClassification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "tag_id")
    private Integer tagId;

    @Column(name = "subcategory_id")
    private Integer subcategoryId;

    public ProductClassification() {
    }

    public ProductClassification(Long productId, Integer tagId, Integer subcategoryId) {
        this.productId = productId;
        this.tagId = tagId;
        this.subcategoryId = subcategoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

}
