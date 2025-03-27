package vn.graybee.response.admin.auth;

public class RolePermissionBasicResponse {

    private int id;

    private int permissionId;

    private String permissionName;

    public RolePermissionBasicResponse() {
    }

    public RolePermissionBasicResponse(int id, int permissionId, String permissionName) {
        this.id = id;
        this.permissionId = permissionId;
        this.permissionName = permissionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

}
