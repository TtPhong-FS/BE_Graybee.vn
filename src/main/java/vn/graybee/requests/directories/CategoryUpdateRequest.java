package vn.graybee.requests.directories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import vn.graybee.enums.CategoryStatus;

import java.util.Collections;
import java.util.List;

public class CategoryUpdateRequest {

    @NotBlank(message = "Không được để trống")
    @Size(max = 35, message = "Độ dài không được vượt quá 35 ký tự")
    private String categoryName;

    private CategoryStatus status;

    private List<Integer> subcategories;

    private List<Integer> manufacturers;

    public List<Integer> getSubcategories() {
        return subcategories != null ? subcategories : Collections.emptyList();
    }

    public void setSubcategories(List<Integer> subcategories) {
        this.subcategories = subcategories;
    }

    public List<Integer> getManufacturers() {
        return manufacturers != null ? manufacturers : Collections.emptyList();
    }

    public void setManufacturers(List<Integer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

}
