package vn.graybee.modules.account.dto.response;


import vn.graybee.auth.enums.Role;

import java.util.List;

public class AccountPrincipal {

    private Long id;

    private String uid;

    private String password;

    private Role role;

    private List<String> permissions;

    private boolean isActive;

    private boolean isSuperAdmin;

    public AccountPrincipal(Long id, String uid, String password, Role role, boolean isActive, boolean isSuperAdmin) {
        this.id = id;
        this.uid = uid;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.isSuperAdmin = isSuperAdmin;
    }

    public AccountPrincipal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

}
