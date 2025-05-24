package vn.graybee.taxonomy.category.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import vn.graybee.common.entity.BaseModel;
import vn.graybee.taxonomy.enums.TaxonomyStatus;

@Entity
@Table(name = "category_classifications", uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "subcategory_id", "manufacturer_id"}))
public class CategoryClassification extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "subcategory_id")
    private Integer subcategoryId;

    @Column(name = "manufacturer_id")
    private Integer manufacturerId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaxonomyStatus status;

    public CategoryClassification() {
    }

    public TaxonomyStatus getStatus() {
        return status;
    }

    public void setStatus(TaxonomyStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

}
