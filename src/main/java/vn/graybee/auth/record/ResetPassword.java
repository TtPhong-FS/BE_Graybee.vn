package vn.graybee.auth.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPassword {

    @NotBlank(message = "{auth.validation.password.not.blank}")
    @Size(min = 6, max = 100, message = "{auth.validation.password.size}")
    private String password;

    @NotBlank(message = "{auth.validation.password.not.blank}")
    @Size(min = 6, max = 100, message = "{auth.validation.repeat_password.size}")
    private String repeatPassword;

}
