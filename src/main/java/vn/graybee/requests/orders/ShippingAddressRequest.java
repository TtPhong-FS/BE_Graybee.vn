package vn.graybee.requests.orders;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ShippingAddressRequest {

    @NotNull(message = "Vui lòng nhập họ và tên người nhận hàng")
    @Size(max = 100, message = "Độ dài tối đa 100 ký tự")
    private String fullName;

    @Size(max = 12, message = "Độ dài tối đa 12 ký tự")
    @NotNull(message = "Vui lòng nhập số điện thoại người nhận hàng")
    private String phoneNumber;

    @Size(max = 50, message = "Độ dài tối đa 50 ký tự")
    @NotNull(message = "Chọn thành phố/tiểu bang")
    private String city;

    @Size(max = 150, message = "Độ dài tối đa 150 ký tự")
    @NotNull(message = "Nhập địa chỉ nhận hàng")
    private String streetAddress;

    @Size(max = 50, message = "Độ dài tối đa 50 ký tự")
    @NotNull(message = "Chọn xã")
    private String commune;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự")
    @NotNull(message = "Chọn huyện/quận")
    private String district;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

}
