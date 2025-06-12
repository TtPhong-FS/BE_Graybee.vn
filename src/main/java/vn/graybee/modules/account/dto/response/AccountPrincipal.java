package vn.graybee.modules.account.dto.response;


import lombok.Getter;
import lombok.Setter;
import vn.graybee.auth.enums.Role;

import java.util.List;

@Setter
@Getter
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


}
