package vn.graybee.permission.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PermissionRequest {

    @NotBlank(message = "Tên quyền không thể trống")
    @Size(max = 50, message = "Độ dài tối đa không vượt quá 50 ký tự")
    private String name;

    @Size(max = 150, message = "Độ dài tối đa không vượt quá 150 ký tự")
    private String description;

    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
