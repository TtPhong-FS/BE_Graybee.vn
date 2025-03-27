package vn.graybee.response.admin.auth;

import vn.graybee.models.users.Role;
import vn.graybee.response.BaseResponse;

import java.time.LocalDateTime;
import java.util.List;

public class RoleResponse extends BaseResponse {

    private int id;

    private String name;

    private List<PermissionBasicResponse> permissions;

    private int userCount;

    private String status;

    public RoleResponse(LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
    }

    public RoleResponse(Role role, List<PermissionBasicResponse> permissions) {
        super(role.getCreatedAt(), role.getUpdatedAt());
        this.id = role.getId();
        this.name = role.getName();
        this.permissions = permissions;
        this.userCount = role.getUserCount();
        this.status = role.getStatus();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
