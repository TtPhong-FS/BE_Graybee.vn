package vn.graybee.response.admin.directories.category;

import vn.graybee.enums.DirectoryStatus;
import vn.graybee.models.directories.Category;
import vn.graybee.response.BaseResponse;
import vn.graybee.response.admin.directories.manufacturer.ManuDto;
import vn.graybee.response.admin.directories.subcate.SubcateDto;

import java.time.LocalDateTime;
import java.util.List;

public class CategoryResponse extends BaseResponse {

    private int id;

    private String name;

    private List<SubcateDto> subcategories;

    private List<ManuDto> manufacturers;

    private DirectoryStatus status;

    private int productCount;

    public CategoryResponse(LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
    }

    public CategoryResponse(Category category) {
        super(category.getCreatedAt(), category.getUpdatedAt());
        this.id = category.getId();
        this.name = category.getName();
        this.status = category.getStatus();
        this.productCount = category.getProductCount();
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

    public DirectoryStatus getStatus() {
        return status;
    }

    public void setStatus(DirectoryStatus status) {
        this.status = status;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

}
