package vn.graybee.requests.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.List;

public class RoleUpdateRequest {

    @NotBlank(message = "Tên vai trò không thể trống")
    @Size(max = 20, message = "Độ dài tối đa không vượt quá 20 ký tự")
    private String name;

    @NotBlank(message = "Trạng thái không thể trống")
    @Size(max = 10, message = "Độ dài tối đa không vượt quá 10 ký tự")
    private String status;

    private List<Integer> permissions;

    public List<Integer> getPermissions() {
        return permissions != null ? permissions : Collections.emptyList();
    }

    public void setPermissions(List<Integer> permissions) {
        this.permissions = permissions;
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
