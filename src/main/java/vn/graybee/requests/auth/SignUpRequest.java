package vn.graybee.requests.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class SignUpRequest {

    @Size(max = 100, message = "Độ dài tối đa không vượt quá 100 ký tự")
    private String fullName;

    @NotBlank(message = "Vui lòng nhập Số điện thoại")
    @Size(max = 12, message = "Số điện thoại chỉ được từ 10 - 12 số")
    private String phoneNumber;

    @Size(max = 50, message = "Độ dài tối đa không vượt quá 50 ký tự")
    private String email;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(max = 100, message = "Độ dài tối đa không vượt quá 100 ký tự")
    private String password;

    private LocalDate dateOfBirth;

    @Pattern(regexp = "^(?i)(male|female)$", message = "Giới tính chỉ được là male hoặc female")
    @Size(max = 6, message = "Vui lòng chọn Male hoặc Female")
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
