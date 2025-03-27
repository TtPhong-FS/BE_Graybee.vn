package vn.graybee.response.admin.auth;

import vn.graybee.models.users.Permission;
import vn.graybee.response.BaseResponse;

import java.time.LocalDateTime;

public class PermissionResponse extends BaseResponse {

    private int id;

    private String name;

    private int userCount;

    private String description;

    private String status;

    public PermissionResponse(Permission permission) {
        super(permission.getCreatedAt(), permission.getUpdatedAt());
        this.id = permission.getId();
        this.name = permission.getName();
        this.userCount = permission.getUserCount();
        this.description = permission.getDescription();
        this.status = permission.getStatus();
    }

    public PermissionResponse(LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
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
