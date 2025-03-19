package vn.graybee.response.categories;

import vn.graybee.models.categories.Category;
import vn.graybee.response.BaseResponse;

import java.time.LocalDateTime;

public class CategoryResponse extends BaseResponse {

    private int id;

    private String categoryName;

    private String status;

    private int productCount;


    public CategoryResponse(LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
    }

    public CategoryResponse(LocalDateTime createdAt, LocalDateTime updatedAt, Category category) {
        super(createdAt, updatedAt);
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.status = category.getStatus();
        this.productCount = category.getProductCount();
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
