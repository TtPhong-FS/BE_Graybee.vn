package vn.graybee.taxonomy.category.dto.response;

public class CategoryManufacturerBasicDto {

    private int categoryId;

    private int manufacturerId;

    private String manufacturerName;

    public CategoryManufacturerBasicDto(int categoryId, int manufacturerId, String manufacturerName) {
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
        this.manufacturerName = manufacturerName;
    }

    public CategoryManufacturerBasicDto() {
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

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

}
