package vn.graybee.requests.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import vn.graybee.enums.CategoryStatus;

public class CategoryUpdateRequest {

    @NotBlank(message = "Không được để trống")
    @Size(max = 35, message = "Độ dài không được vượt quá 35 ký tự")
    private String name;
    
    private CategoryStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

}
