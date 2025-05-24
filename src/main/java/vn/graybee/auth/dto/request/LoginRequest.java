package vn.graybee.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Vui lòng nhập Số điện thoại")
    @Size(max = 12, message = "Số điện thoại chỉ được từ 10 - 12 số")
    private String phone;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(max = 100, message = "Độ dài tối đa không vượt quá 100 ký tự")
    private String password;

    public LoginRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
