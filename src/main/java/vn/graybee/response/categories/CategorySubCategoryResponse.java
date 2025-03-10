package vn.graybee.response.categories;

import vn.graybee.enums.CategoryStatus;

public class CategorySubCategoryResponse {

    private int id;

    private int categoryId;

    private int subCategoryId;

    private CategoryStatus status;

    public CategorySubCategoryResponse(int id, int categoryId, int subCategoryId, CategoryStatus status) {
        this.id = id;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

}
