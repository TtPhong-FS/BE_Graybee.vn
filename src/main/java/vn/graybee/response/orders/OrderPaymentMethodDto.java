package vn.graybee.response.orders;

public class OrderPaymentMethodDto {

    private long orderId;

    private String paymentMethod;

    public OrderPaymentMethodDto(long orderId, String paymentMethod) {
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
