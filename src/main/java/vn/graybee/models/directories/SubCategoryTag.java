package vn.graybee.models.directories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "subcategories_tags", uniqueConstraints = @UniqueConstraint(columnNames = {"subcategory_id", "tag_id"}))
public class SubCategoryTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subcategory_id", nullable = false)
    private Integer subcategoryId;

    @Column(name = "tag_id", nullable = false)
    private Integer tagId;

    public SubCategoryTag(Integer subcategoryId, Integer tagId) {
        this.subcategoryId = subcategoryId;
        this.tagId = tagId;
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

}
