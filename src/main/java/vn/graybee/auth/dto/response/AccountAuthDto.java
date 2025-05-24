package vn.graybee.auth.dto.response;

import vn.graybee.auth.enums.Role;

public class AccountAuthDto {

    private String uid;

    private Role role;

    public AccountAuthDto() {
    }

    public AccountAuthDto(String uid, Role role) {
        this.uid = uid;
        this.role = role;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
