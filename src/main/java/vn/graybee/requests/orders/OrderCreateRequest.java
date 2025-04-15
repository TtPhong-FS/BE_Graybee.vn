package vn.graybee.requests.orders;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.enums.DeliveryType;
import vn.graybee.enums.PaymentMethod;
import vn.graybee.enums.ShippingMethod;
import vn.graybee.exceptions.BusinessCustomException;

import java.util.Collections;
import java.util.List;

public class OrderCreateRequest {

    private List<Integer> cartItemIds;

    private boolean useExistingAddress;

    private Integer addressId;

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

    @Size(max = 200, message = "Độ dài tối đa không vượt quá 200 ký tự")
    private String note;

    @NotBlank(message = "Chọn phương thức thanh toán")
    private String paymentMethod;

    @NotBlank(message = "Chọn phương thức vận chuyển")
    private String shippingMethod;

    @NotBlank(message = "Chọn hình thức nhận hàng")
    private String deliveryType;

    @Size(max = 100, message = "Mã giảm giá không đúng")
    private String discountCode;

    private boolean issueInvoices;

    public PaymentMethod getPaymentMethodEnum() {
        try {
            return PaymentMethod.valueOf(paymentMethod.toUpperCase());
        } catch (RuntimeException e) {
            throw new BusinessCustomException(ConstantGeneral.status, ConstantGeneral.method_invalid + paymentMethod);
        }
    }

    public ShippingMethod getShippingMethodEnum() {
        try {
            return ShippingMethod.valueOf(shippingMethod.toUpperCase());
        } catch (RuntimeException e) {
            throw new BusinessCustomException(ConstantGeneral.status, ConstantGeneral.method_invalid + shippingMethod);
        }
    }


    public DeliveryType getDeliveryTypeEnum() {
        try {
            return DeliveryType.valueOf(deliveryType.toUpperCase());
        } catch (RuntimeException e) {
            throw new BusinessCustomException(ConstantGeneral.status, ConstantGeneral.method_invalid + deliveryType);
        }
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

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public List<Integer> getCartItemIds() {
        return cartItemIds != null ? cartItemIds : Collections.emptyList();
    }

    public void setCartItemIds(List<Integer> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }

    public boolean isUseExistingAddress() {
        return useExistingAddress;
    }

    public void setUseExistingAddress(boolean useExistingAddress) {
        this.useExistingAddress = useExistingAddress;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public boolean isIssueInvoices() {
        return issueInvoices;
    }

    public void setIssueInvoices(boolean issueInvoices) {
        this.issueInvoices = issueInvoices;
    }

}
