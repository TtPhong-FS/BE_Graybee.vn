package vn.graybee.modules.account.dto.request.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeUsernameRequest {

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(max = 100, message = "Tên đăng nhập quá dài")
    private String newUsername;

}
