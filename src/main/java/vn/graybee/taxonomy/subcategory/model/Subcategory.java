package vn.graybee.taxonomy.subcategory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import vn.graybee.common.entity.BaseModel;
import vn.graybee.taxonomy.enums.TaxonomyStatus;

@Entity
@Table(name = "subcategories")
public class Subcategory extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 35, nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaxonomyStatus status;

    public Subcategory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaxonomyStatus getStatus() {
        return status;
    }

    public void setStatus(TaxonomyStatus status) {
        this.status = status;
    }

}
