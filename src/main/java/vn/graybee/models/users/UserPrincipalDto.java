package vn.graybee.models.users;

import java.util.Collections;
import java.util.List;

public class UserPrincipalDto {

    private Integer id;

    private Integer userUid;

    private boolean isActive;

    private Integer roleId;

    private String username;

    private String password;

    private String ROLE_NAME;

    private List<String> permissions;

    public UserPrincipalDto() {
    }

    public UserPrincipalDto(Integer id, Integer userUid, boolean isActive, Integer roleId, String username, String password, String ROLE_NAME) {
        this.id = id;
        this.userUid = userUid;
        this.isActive = isActive;
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.ROLE_NAME = ROLE_NAME;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public String getROLE_NAME() {
        return ROLE_NAME;
    }

    public void setROLE_NAME(String ROLE_NAME) {
        this.ROLE_NAME = ROLE_NAME;
    }

    public List<String> getPermissions() {
        return permissions != null ? permissions : Collections.emptyList();
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

}
