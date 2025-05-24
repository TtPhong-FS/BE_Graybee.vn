package vn.graybee.response.users;


import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.account.enums.Gender;

import java.util.Date;

public class UserProfileResponse {

    private String uid;

    private String fullName;

    private String phone;

    private String email;

    private Gender gender;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirth;

    public UserProfileResponse() {
    }

    public UserProfileResponse(String uid, String fullName, String phone, String email, Gender gender, Date dateOfBirth) {
        this.uid = uid;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
