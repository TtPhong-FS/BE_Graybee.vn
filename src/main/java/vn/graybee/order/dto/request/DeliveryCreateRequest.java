package vn.graybee.order.dto.request;

import jakarta.validation.constraints.NotBlank;

public class DeliveryCreateRequest {

    @NotBlank(message = "Chọn phương thức vận chuyển")
    private String shippingMethod;

    @NotBlank(message = "Chọn hình thức nhận hàng")
    private String deliveryType;

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

}
