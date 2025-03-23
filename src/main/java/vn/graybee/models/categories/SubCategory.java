package vn.graybee.models.categories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import vn.graybee.models.others.BaseModel;

@Entity
@Table(name = "subcategories")
public class SubCategory extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subcategory_name", length = 35, nullable = false, unique = true)
    private String subcategoryName;

    @Column(length = 30)
    private String status;

    public SubCategory() {
    }

    public SubCategory(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
