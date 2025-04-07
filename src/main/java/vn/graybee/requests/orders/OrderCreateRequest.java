package vn.graybee.requests.orders;

import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.List;

public class OrderCreateRequest {
    
    private List<Integer> cartItemIds;

    private boolean useExistingAddress;

    private ShippingAddressRequest shippingAddress;

    @Size(max = 200, message = "Độ dài tối đa không vượt quá 200 ký tự")
    private String note;

    @Size(max = 35, message = "Độ dài tối đa không vượt quá 35 ký tự")
    private String paymentMethod;

    @Size(max = 35, message = "Độ dài tối đa không vượt quá 35 ký tự")
    private String deliveryType;

    @Size(max = 100, message = "Mã giảm giá không đúng")
    private String discountCode;

    private boolean issueInvoices;

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

    public ShippingAddressRequest getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressRequest shippingAddress) {
        this.shippingAddress = shippingAddress;
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
