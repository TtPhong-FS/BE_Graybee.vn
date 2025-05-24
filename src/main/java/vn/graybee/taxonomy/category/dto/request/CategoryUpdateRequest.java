package vn.graybee.taxonomy.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CategoryUpdateRequest {

    @NotBlank(message = "{category.name.not_blank}")
    @Size(min = 2, max = 35, message = "{category.name.size}")
    private String name;

    @NotBlank(message = "{common.status.not_blank}")
    private String status;

    private List<String> subcategories;

    private List<String> manufacturers;


    public CategoryUpdateRequest() {
    }

    public List<String> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<String> subcategories) {
        this.subcategories = subcategories;
    }

    public List<String> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<String> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
