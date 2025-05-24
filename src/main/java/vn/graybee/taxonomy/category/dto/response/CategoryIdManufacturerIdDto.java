package vn.graybee.taxonomy.category.dto.response;

public class CategoryIdManufacturerIdDto {

    private int categoryId;

    private int manufacturerId;

    public CategoryIdManufacturerIdDto() {
    }

    public CategoryIdManufacturerIdDto(int categoryId, int manufacturerId) {
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
