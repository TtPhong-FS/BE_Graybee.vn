package vn.graybee.requests.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ManufacturerCreateRequest {

    @NotBlank(message = "Không được để trống")
    @Size(max = 50, message = "Độ dài không được vượt quá 50 ký tự")
    private String manufacturerName;

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

}

