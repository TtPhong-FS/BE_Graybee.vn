package vn.graybee.requests.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddressCreateRequest {

    @NotBlank(message = "Nhập họ và tên người nhận")
    @Size(max = 50, message = "Họ và tên quá dài")
    private String fullname;

    @NotBlank(message = "Nhập số điện thoại người nhận")
    @Size(min = 10, max = 12, message = "Số điện thoại phải từ 10 - 12 số")
    private String phoneNumber;

    @NotBlank(message = "Chọn thành phố/tỉnh")
    @Size(max = 50, message = "Tên thành phố quá dài")
    private String city;

    @NotBlank(message = "Chọn huyện/quận")
    @Size(max = 50, message = "Tên huyện quá dài")
    private String district;

    @NotBlank(message = "Chọn xã/phường")
    @Size(max = 50, message = "Tên xã quá dài")
    private String commune;

    @NotBlank(message = "Nhập địa chỉ cụ thể")
    @Size(max = 150, message = "Địa chỉ quá dài")
    private String streetAddress;

    @JsonProperty("default")
    private boolean isDefault;

    public AddressCreateRequest() {
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

}
