package vn.graybee.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    @NotBlank(message = "{account.validation.phone.not_blank}")
    @Size(max = 100, message = "{account.validation.email.too-long}")
    private String email;

    @NotBlank(message = "{account.validation.password.not_blank}")
    @Size(max = 100, message = "{account.validation.password.too-long}")
    private String password;

}
