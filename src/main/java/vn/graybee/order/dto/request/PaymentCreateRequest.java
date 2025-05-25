package vn.graybee.order.dto.request;

import jakarta.validation.constraints.NotBlank;

public class PaymentCreateRequest {


    @NotBlank(message = "Chọn hình thức nhận hàng")
    private String paymentMethod;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
