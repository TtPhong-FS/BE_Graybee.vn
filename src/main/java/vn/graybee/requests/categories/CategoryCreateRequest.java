package vn.graybee.requests.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.List;

public class CategoryCreateRequest {

    @NotBlank(message = "Không được để trống")
    @Size(max = 35, message = "Độ dài không được vượt quá 35 ký tự")
    private String name;

    private List<String> subCategories;

    private List<String> manufacturers;

    public List<String> getManufacturers() {
        return manufacturers != null ? manufacturers : Collections.emptyList();
    }

    public void setManufacturers(List<String> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<String> getSubCategories() {
        return subCategories != null ? subCategories : Collections.emptyList();
    }

    public void setSubCategories(List<String> subCategories) {
        this.subCategories = subCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
