package vn.graybee.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.account.dto.response.ProfileResponse;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    private ProfileResponse profile;

    private AuthDto auth;

}
