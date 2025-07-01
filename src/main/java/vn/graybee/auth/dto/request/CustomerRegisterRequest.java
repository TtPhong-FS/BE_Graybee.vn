package vn.graybee.auth.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import vn.graybee.modules.account.dto.request.ProfileRequest;

@Setter
@Getter
public class CustomerRegisterRequest {

    @NotBlank(message = "{auth.validation.email.not.blank}")
    @Size(max = 100, message = "{auth.validation.email.max}")
    private String email;

    @Size(min = 6, max = 100, message = "{auth.validation.password.size}")
    @NotBlank(message = "{auth.validation.password.not.blank}")
    private String password;

    @NotBlank(message = "{auth.validation.repeat_password.not.blank}")
    @Size(min = 6, max = 100, message = "{auth.validation.repeat_password.size}")
    private String repeatPassword;

    @Valid
    private ProfileRequest profile;


}
