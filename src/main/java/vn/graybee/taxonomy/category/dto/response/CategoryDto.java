package vn.graybee.taxonomy.category.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.taxonomy.category.model.Category;
import vn.graybee.taxonomy.enums.TaxonomyStatus;
import vn.graybee.taxonomy.manufacturer.dto.response.ManufacturerBasicDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryBasicDto;

import java.time.LocalDateTime;
import java.util.List;

public class CategoryDto {

    private int id;

    private String name;

    private List<SubcategoryBasicDto> subcategories;

    private List<ManufacturerBasicDto> manufacturers;

    private TaxonomyStatus status;

    private int productCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.status = category.getStatus();
        this.productCount = category.getProductCount();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
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

    public List<SubcategoryBasicDto> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryBasicDto> subcategories) {
        this.subcategories = subcategories;
    }

    public List<ManufacturerBasicDto> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<ManufacturerBasicDto> manufacturers) {
        this.manufacturers = manufacturers;
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

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

}
