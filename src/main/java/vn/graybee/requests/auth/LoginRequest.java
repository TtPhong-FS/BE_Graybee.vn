package vn.graybee.requests.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Vui lòng nhập Số điện thoại")
    @Size(max = 12, message = "Số điện thoại chỉ được từ 10 - 12 số")
    private String username;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(max = 100, message = "Độ dài tối đa không vượt quá 100 ký tự")
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
