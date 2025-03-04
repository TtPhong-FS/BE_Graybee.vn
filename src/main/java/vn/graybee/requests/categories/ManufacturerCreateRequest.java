package vn.graybee.requests.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ManufacturerCreateRequest {

    @NotBlank(message = "Không được để trống")
    @Size(max = 50, message = "Độ dài không được vượt quá 50 ký tự")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
