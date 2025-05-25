package vn.graybee.account.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddressCreateRequest {

    @NotBlank(message = "Nhập họ và tên người nhận")
    @Size(max = 50, message = "Họ và tên quá dài")
    private String recipientName;

    @NotBlank(message = "Nhập số điện thoại người nhận")
    @Size(min = 10, max = 12, message = "Số điện thoại phải từ 10 - 12 số")
    private String phone;

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
    private String street;

    @JsonProperty("default")
    private boolean isDefault;

    public AddressCreateRequest() {
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

}
