package vn.graybee.requests.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PermissionUpdateRequest {

    @NotBlank(message = "Tên quyền không thể trống")
    @Size(max = 50, message = "Độ dài tối đa không vượt quá 50 ký tự")
    private String name;

    @Size(max = 150, message = "Độ dài tối đa không vượt quá 150 ký tự")
    private String description;

    @NotBlank(message = "Trạng thái không thể trống")
    @Size(max = 10, message = "Độ dài tối đa không vượt quá 10 ký tự")
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
