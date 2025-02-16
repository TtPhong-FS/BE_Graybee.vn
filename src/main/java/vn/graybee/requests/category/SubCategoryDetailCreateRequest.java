package vn.graybee.requests.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SubCategoryDetailCreateRequest {

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
