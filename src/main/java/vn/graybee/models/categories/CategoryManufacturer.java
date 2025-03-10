package vn.graybee.models.categories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import vn.graybee.enums.CategoryStatus;
import vn.graybee.models.others.BaseModel;

@Entity
@Table(name = "categories_manufacturers", uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "manufacturer_id"}))
public class CategoryManufacturer extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "manufacturer_id", nullable = false)
    private Integer manufacturerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CategoryStatus status;

    public CategoryManufacturer(Integer categoryId, Integer manufacturerId, CategoryStatus status) {
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
        this.status = status;
    }

    public CategoryManufacturer() {
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
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

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

}
