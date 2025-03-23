package vn.graybee.response.admin.directories.category;

public class CategoryManufacturerIdResponse {

    private int categoryId;

    private int manufacturerId;

    public CategoryManufacturerIdResponse() {
    }

    public CategoryManufacturerIdResponse(int categoryId, int manufacturerId) {
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

}
