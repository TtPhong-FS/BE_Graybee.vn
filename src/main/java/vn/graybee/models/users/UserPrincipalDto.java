package vn.graybee.models.users;

import java.util.Collections;
import java.util.List;

public class UserPrincipalDto {

    private Integer id;

    private Integer userUid;

    private boolean userActive;

    private boolean isSuperAdmin;

    private String username;

    private String password;

    private Integer roleId;

    private String roleName;

    private boolean roleActive;

    private List<String> permissions;

    public UserPrincipalDto() {
    }

    public UserPrincipalDto(Integer id, Integer userUid, boolean userActive, boolean isSuperAdmin, String username, String password, Integer roleId, String roleName) {
        this.id = id;
        this.userUid = userUid;
        this.userActive = userActive;
        this.isSuperAdmin = isSuperAdmin;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public boolean isUserActive() {
        return userActive;
    }

    public void setUserActive(boolean userActive) {
        this.userActive = userActive;
    }

    public boolean isRoleActive() {
        return roleActive;
    }

    public void setRoleActive(boolean roleActive) {
        this.roleActive = roleActive;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    public Integer getUserUid() {
        return userUid;
    }

    public void setUserUid(Integer userUid) {
        this.userUid = userUid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getPermissions() {
        return permissions != null ? permissions : Collections.emptyList();
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

}
