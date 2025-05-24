package vn.graybee.taxonomy.manufacturer.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.taxonomy.enums.TaxonomyStatus;
import vn.graybee.taxonomy.manufacturer.model.Manufacturer;

import java.time.LocalDateTime;

public class ManufacturerDto {

    private int id;

    private String name;

    private int productCount;

    private TaxonomyStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public ManufacturerDto(Manufacturer manufacturer) {
        this.id = manufacturer.getId();
        this.name = manufacturer.getName();
        this.status = manufacturer.getStatus();
        this.productCount = manufacturer.getProductCount();
        this.createdAt = manufacturer.getCreatedAt();
        this.updatedAt = manufacturer.getUpdatedAt();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
