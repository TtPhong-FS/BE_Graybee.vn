package vn.graybee.response.users;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class UserProfileResponse {

    private Integer uid;

    private String fullName;

    private String phoneNumber;

    private String email;

    private String gender;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate dateOfBirth;

    public UserProfileResponse() {
    }

    public UserProfileResponse(Integer uid, String fullName, String phoneNumber, String email, String gender, LocalDate dateOfBirth) {
        this.uid = uid;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
