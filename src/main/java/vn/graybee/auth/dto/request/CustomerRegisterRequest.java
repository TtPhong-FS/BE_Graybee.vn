package vn.graybee.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import vn.graybee.account.enums.Gender;

import java.util.Date;

public class CustomerRegisterRequest {

    @Size(max = 100, message = "Độ dài tối đa không vượt quá 100 ký tự")
    private String fullName;

    @NotBlank(message = "Vui lòng nhập Số điện thoại")
    @Size(max = 12, message = "Số điện thoại chỉ được từ 10 - 12 số")
    private String phone;

    @Size(max = 50, message = "Độ dài tối đa không vượt quá 50 ký tự")
    private String email;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    @Size(max = 100, message = "Độ dài tối đa không vượt quá 100 ký tự")
    private String password;

    private Date birthDay;

    private Gender gender;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

}
