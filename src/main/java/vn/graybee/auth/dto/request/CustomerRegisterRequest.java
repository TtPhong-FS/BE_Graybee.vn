package vn.graybee.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CustomerRegisterRequest {

    @Size(max = 100, message = "Độ dài tối đa không vượt quá 100 ký tự")
    private String fullName;

    @NotBlank(message = "auth.phone.not_blank")
    @Size(max = 12, message = "Số điện thoại chỉ được từ 10 - 12 số")
    private String phone;

    @NotBlank(message = "auth.email.not_blank")
    @Size(max = 50, message = "Độ dài tối đa không vượt quá 50 ký tự")
    private String email;

    @NotBlank(message = "auth.password.not_blank")
    @Size(max = 100, message = "Độ dài tối đa không vượt quá 100 ký tự")
    private String password;

    private Date birthDay;

    private String gender;


}
