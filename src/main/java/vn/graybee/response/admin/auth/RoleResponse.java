package vn.graybee.response.admin.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.enums.RolePermissionStatus;
import vn.graybee.models.users.Role;

import java.time.LocalDateTime;
import java.util.List;

public class RoleResponse {

    private int id;

    private String name;

    private List<PermissionBasicResponse> permissions;

    private int userCount;

    private RolePermissionStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public RoleResponse(Role role, List<PermissionBasicResponse> permissions) {
        this.id = role.getId();
        this.name = role.getName();
        this.permissions = permissions;
        this.userCount = role.getUserCount();
        this.createdAt = role.getCreatedAt();
        this.updatedAt = role.getUpdatedAt();
        this.status = role.getStatus();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<PermissionBasicResponse> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionBasicResponse> permissions) {
        this.permissions = permissions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public RolePermissionStatus getStatus() {
        return status;
    }

    public void setStatus(RolePermissionStatus status) {
        this.status = status;
    }

}
