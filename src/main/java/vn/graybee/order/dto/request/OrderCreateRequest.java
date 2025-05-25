package vn.graybee.order.dto.request;

import jakarta.validation.constraints.Size;
import vn.graybee.account.dto.request.AddressCreateRequest;

import java.util.List;

public class OrderCreateRequest {

    private List<Integer> cartItemIds;

    private boolean useExistingAddress;

    private Integer addressId;

    @Size(max = 200, message = "Độ dài tối đa không vượt quá 200 ký tự")
    private String note;

    private AddressCreateRequest addressCreateRequest;

    private DeliveryCreateRequest deliveryCreateRequest;

    private PaymentCreateRequest paymentCreateRequest;

    @Size(max = 100, message = "Mã giảm giá không đúng")
    private String discountCode;

    private boolean issueInvoices;

    public List<Integer> getCartItemIds() {
        return cartItemIds;
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

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AddressCreateRequest getAddressCreateRequest() {
        return addressCreateRequest;
    }

    public void setAddressCreateRequest(AddressCreateRequest addressCreateRequest) {
        this.addressCreateRequest = addressCreateRequest;
    }

    public DeliveryCreateRequest getDeliveryCreateRequest() {
        return deliveryCreateRequest;
    }

    public void setDeliveryCreateRequest(DeliveryCreateRequest deliveryCreateRequest) {
        this.deliveryCreateRequest = deliveryCreateRequest;
    }

    public PaymentCreateRequest getPaymentCreateRequest() {
        return paymentCreateRequest;
    }

    public void setPaymentCreateRequest(PaymentCreateRequest paymentCreateRequest) {
        this.paymentCreateRequest = paymentCreateRequest;
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
