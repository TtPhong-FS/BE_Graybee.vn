package vn.graybee.response.orders;

import vn.graybee.enums.PaymentMethod;

public class OrderPaymentMethodDto {

    private long orderId;

    private PaymentMethod paymentMethod;

    public OrderPaymentMethodDto(long orderId, PaymentMethod paymentMethod) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
    }

    public OrderPaymentMethodDto() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
