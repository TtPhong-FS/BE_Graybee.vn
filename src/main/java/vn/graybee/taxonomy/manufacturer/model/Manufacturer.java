package vn.graybee.taxonomy.manufacturer.model;

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
@Table(name = "manufacturers")
public class Manufacturer extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 35, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaxonomyStatus status;

    @Column(name = "product_count")
    private int productCount;

    public Manufacturer() {
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

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

}
