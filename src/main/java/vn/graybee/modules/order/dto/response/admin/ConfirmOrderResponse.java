package vn.graybee.modules.order.dto.response.admin;

import vn.graybee.modules.order.enums.OrderStatus;

public class ConfirmOrderResponse {

    private long orderId;

    private boolean isConfirmed;

    private OrderStatus status;

    public ConfirmOrderResponse(long orderId, boolean isConfirmed, OrderStatus status) {
        this.orderId = orderId;
        this.isConfirmed = isConfirmed;
        this.status = status;
    }

    public ConfirmOrderResponse() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}
