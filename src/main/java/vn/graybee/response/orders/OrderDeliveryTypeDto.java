package vn.graybee.response.orders;

public class OrderDeliveryTypeDto {

    private long orderId;

    private String deliveryType;

    public OrderDeliveryTypeDto() {
    }

    public OrderDeliveryTypeDto(long orderId, String deliveryType) {
        this.orderId = orderId;
        this.deliveryType = deliveryType;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

}
