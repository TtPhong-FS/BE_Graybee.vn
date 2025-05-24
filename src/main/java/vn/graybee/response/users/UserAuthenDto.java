package vn.graybee.response.users;

import vn.graybee.auth.enums.Role;

public class UserAuthenDto {

    private String password;

    private Role role;

    private String uid;

    private boolean isActive;

    public UserAuthenDto() {
    }

    public UserAuthenDto(String password, Role role, String uid, boolean isActive) {
        this.password = password;
        this.role = role;
        this.uid = uid;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
