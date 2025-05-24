package vn.graybee.taxonomy.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CategoryCreateRequest {

    @NotBlank(message = "{category.name.not_blank}")
    @Size(min = 2, max = 35, message = "{category.name.size}")
    private String name;

    private List<String> subcategories;

    private List<String> manufacturers;

    public List<String> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<String> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<String> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<String> subcategories) {
        this.subcategories = subcategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
