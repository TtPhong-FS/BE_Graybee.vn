package vn.graybee.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.auth.enums.Role;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountAuthDto {

    private Long id;

    private String uid;

    private Role role;

    private String password;

    private boolean isActive;

}
