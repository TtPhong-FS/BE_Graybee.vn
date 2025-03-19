package vn.graybee.response.categories;

import java.util.List;

public class CategoryWithManufacturersResponse {

    private int id;

    private String categoryName;

    private List<ManufacturerSummaryResponse> manufacturers;

    public CategoryWithManufacturersResponse(int id, String categoryName, List<ManufacturerSummaryResponse> manufacturers) {
        this.id = id;
        this.categoryName = categoryName;
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

    public List<ManufacturerSummaryResponse> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<ManufacturerSummaryResponse> manufacturers) {
        this.manufacturers = manufacturers;
    }

}
