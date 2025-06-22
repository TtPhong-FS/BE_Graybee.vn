package vn.graybee.modules.account.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.auth.enums.Role;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class AccountAuthResponse {

    private long id;

    private String uid;

    private String username;

    private Role role;

    private boolean isActive;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginAt;

    public AccountAuthResponse(long id, String uid, String username, Role role, boolean isActive, LocalDateTime lastLoginAt) {
        this.id = id;
        this.uid = uid;
        this.username = username;
        this.role = role;
        this.isActive = isActive;
        this.lastLoginAt = lastLoginAt;
    }

}
