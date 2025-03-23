package vn.graybee.models.categories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import vn.graybee.models.others.BaseModel;

@Entity
@Table(name = "manufacturers")
public class Manufacturer extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "manufacturer_name", nullable = false, length = 50, unique = true)
    private String manufacturerName;

    @Column(length = 30)
    private String status;

    @Column(name = "product_count")
    private int productCount;

    public Manufacturer() {
    }

    public Manufacturer(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public void increaseProductCount() {
        this.productCount++;
    }

    public void decreaseProductCount() {
        if (this.productCount > 0) {
            this.productCount--;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

}
