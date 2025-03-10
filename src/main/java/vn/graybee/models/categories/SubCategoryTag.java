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
@Table(name = "subcategories_tags", uniqueConstraints = @UniqueConstraint(columnNames = {"subcategory_id", "tag_id"}))
public class SubCategoryTag extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subcategory_id", nullable = false)
    private Integer subcategoryId;

    @Column(name = "tag_id", nullable = false)
    private Integer tagId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CategoryStatus status;

    public SubCategoryTag(Integer subcategoryId, Integer tagId, CategoryStatus status) {
        this.subcategoryId = subcategoryId;
        this.tagId = tagId;
        this.status = status;
    }

    public SubCategoryTag() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

}
