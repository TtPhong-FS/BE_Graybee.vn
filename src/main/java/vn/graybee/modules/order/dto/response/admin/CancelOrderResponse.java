package vn.graybee.modules.order.dto.response.admin;

import vn.graybee.modules.order.enums.OrderStatus;

public class CancelOrderResponse {

    private long orderId;

    private boolean isCancelled;

    private OrderStatus status;

    public CancelOrderResponse() {
    }

    public CancelOrderResponse(long orderId, boolean isCancelled, OrderStatus status) {
        this.orderId = orderId;
        this.isCancelled = isCancelled;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}
