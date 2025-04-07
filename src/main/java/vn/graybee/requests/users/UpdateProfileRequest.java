package vn.graybee.requests.users;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UpdateProfileRequest {

    @Size(max = 50, message = "Địa chỉ email quá dài")
    private String email;

    @Size(max = 100, message = "Họ và tên quá dài")
    private String fullName;

    @Pattern(regexp = "^(?i)(male|female)$", message = "Giới tính chỉ được là male hoặc female")
    @Size(max = 6, message = "Vui lòng chọn Male hoặc Female")
    private String gender;

    private LocalDate dateOfBirth;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
