package vn.graybee.requests.categories;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CategoryCreateRequest {

    @NotEmpty(message = "Category's name cannot be empty")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
