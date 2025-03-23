package vn.graybee.response.admin.directories.category;

import vn.graybee.models.categories.Category;
import vn.graybee.response.BaseResponse;
import vn.graybee.response.admin.directories.manufacturer.ManuDto;
import vn.graybee.response.admin.directories.subcate.SubcateDto;

import java.time.LocalDateTime;
import java.util.List;

public class CategoryResponse extends BaseResponse {

    private int id;

    private String categoryName;

    private List<SubcateDto> subcategories;

    private List<ManuDto> manufacturers;

    private String status;

    private int productCount;

    public CategoryResponse(LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
    }

    public CategoryResponse(Category category, List<SubcateDto> subcategories, List<ManuDto> manufacturers) {
        super(category.getCreatedAt(), category.getUpdatedAt());
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.status = category.getStatus();
        this.productCount = category.getProductCount();
        this.subcategories = subcategories;
        this.manufacturers = manufacturers;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
