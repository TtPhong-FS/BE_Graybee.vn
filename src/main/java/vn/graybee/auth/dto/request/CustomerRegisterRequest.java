package vn.graybee.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CustomerRegisterRequest {

    @Size(max = 100, message = "auth.validation.fullname.size")
    private String fullName;

    @Size(max = 12, message = "auth.validation.phone.size")
    private String phone;

    @NotBlank(message = "auth.validation.email.not.blank")
    @Size(max = 50, message = "auth.validation.email.max")
    private String email;

    @Size(min = 6, max = 100, message = "auth.validation.password.size")
    @NotBlank(message = "auth.validation.password.not.blank")
    private String password;

    @NotBlank(message = "auth.validation.password.not.blank")
    @Size(min = 6, max = 100, message = "auth.validation.password.size")
    private String repeatPassword;

    private Date birthday;

    @NotBlank(message = "auth.validation.gender.not.blank")
    private String gender;


}
