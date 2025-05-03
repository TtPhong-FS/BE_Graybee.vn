package vn.graybee.response.admin.directories.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.models.directories.Category;
import vn.graybee.response.admin.directories.manufacturer.ManuDto;
import vn.graybee.response.admin.directories.subcate.SubcateDto;
import vn.graybee.response.admin.products.StatusResponse;

import java.time.LocalDateTime;
import java.util.List;

public class CategoryResponse {

    private int id;

    private String name;

    private List<SubcateDto> subcategories;

    private List<ManuDto> manufacturers;

    private StatusResponse status;

    private int productCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.status = new StatusResponse(category.getStatus().getCode(), category.getStatus().getDisplayName());
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

    public List<SubcateDto> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcateDto> subcategories) {
        this.subcategories = subcategories;
    }

    public List<ManuDto> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<ManuDto> manufacturers) {
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

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

}
