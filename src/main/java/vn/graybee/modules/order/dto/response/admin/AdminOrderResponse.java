package vn.graybee.modules.order.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.modules.order.enums.DeliveryType;
import vn.graybee.modules.order.enums.OrderStatus;
import vn.graybee.modules.order.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AdminOrderResponse {

    private long orderId;

    private BigDecimal total;

    private String phone;

    private String customerName;

    private String avatarUrl;

    private PaymentStatus paymentStatus;

    private DeliveryType deliveryType;

    private OrderStatus status;

    private boolean isCancelled;

    private boolean isConfirmed;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderAt;

    public AdminOrderResponse(long orderId, BigDecimal total, String phone, String customerName, String avatarUrl, PaymentStatus paymentStatus, DeliveryType deliveryType, OrderStatus status, boolean isCancelled, boolean isConfirmed, LocalDateTime orderAt) {
        this.orderId = orderId;
        this.total = total;
        this.phone = phone;
        this.customerName = customerName;
        this.avatarUrl = avatarUrl;
        this.paymentStatus = paymentStatus;
        this.deliveryType = deliveryType;
        this.status = status;
        this.isCancelled = isCancelled;
        this.isConfirmed = isConfirmed;
        this.orderAt = orderAt;
    }

    public AdminOrderResponse() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
    }

}
