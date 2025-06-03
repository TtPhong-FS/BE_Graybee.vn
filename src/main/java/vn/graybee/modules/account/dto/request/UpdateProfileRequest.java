package vn.graybee.modules.account.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class UpdateProfileRequest {

    @Size(max = 50, message = "Địa chỉ email quá dài")
    private String email;

    @Size(max = 100, message = "Họ và tên quá dài")
    private String fullName;

    @Pattern(regexp = "^(?i)(male|female)$", message = "Giới tính chỉ được là male hoặc female")
    @Size(max = 6, message = "Vui lòng chọn Male hoặc Female")
    private String gender;

    private Date dateOfBirth;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
