package vn.graybee.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    @NotBlank(message = "account.validation.phone.not_blank")
    @Size(max = 12, message = "Số điện thoại chỉ được từ 10 - 12 số")
    private String phone;

    @NotBlank(message = "account.validation.password.not_blank")
    @Size(max = 100, message = "Độ dài tối đa không vượt quá 100 ký tự")
    private String password;


}
