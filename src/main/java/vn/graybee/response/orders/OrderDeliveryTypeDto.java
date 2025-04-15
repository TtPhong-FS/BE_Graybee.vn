package vn.graybee.response.orders;

import vn.graybee.enums.DeliveryType;

public class OrderDeliveryTypeDto {

    private long orderId;

    private DeliveryType deliveryType;

    public OrderDeliveryTypeDto() {
    }

    public OrderDeliveryTypeDto(long orderId, DeliveryType deliveryType) {
        this.orderId = orderId;
        this.deliveryType = deliveryType;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

}
