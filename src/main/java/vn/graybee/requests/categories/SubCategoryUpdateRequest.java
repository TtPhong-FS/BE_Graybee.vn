package vn.graybee.requests.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class SubCategoryUpdateRequest {

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String name;

    private List<String> subcategoryDetails;

    public List<String> getSubcategoryDetails() {
        return subcategoryDetails;
    }

    public void setSubcategoryDetails(List<String> subcategoryDetails) {
        this.subcategoryDetails = subcategoryDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
