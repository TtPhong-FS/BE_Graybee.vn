package vn.graybee.requests.directories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import vn.graybee.enums.CategoryStatus;

public class ManufacturerUpdateRequest {

    @NotBlank(message = "Không được để trống")
    @Size(max = 35, message = "Độ dài không được vượt quá 35 ký tự")
    private String manufacturerName;
    
    private CategoryStatus status;

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

}
