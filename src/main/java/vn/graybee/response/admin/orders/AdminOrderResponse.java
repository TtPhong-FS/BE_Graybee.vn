package vn.graybee.response.admin.orders;

import vn.graybee.enums.DeliveryType;
import vn.graybee.enums.OrderStatus;
import vn.graybee.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AdminOrderResponse {

    private long id;

    private BigDecimal totalAmount;

    private Integer userUid;

    private String fullName;

    private boolean isGuest;

    private PaymentStatus paymentStatus;

    private DeliveryType deliveryType;

    private OrderStatus status;

    private boolean isCancelled;

    private boolean isConfirmed;

    private LocalDateTime createdAt;

    public AdminOrderResponse(long id, BigDecimal totalAmount, Integer userUid, String fullName, boolean isGuest, PaymentStatus paymentStatus, DeliveryType deliveryType, OrderStatus status, boolean isCancelled, boolean isConfirmed, LocalDateTime createdAt) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.userUid = userUid;
        this.fullName = fullName;
        this.isGuest = isGuest;
        this.paymentStatus = paymentStatus;
        this.deliveryType = deliveryType;
        this.status = status;
        this.isCancelled = isCancelled;
        this.isConfirmed = isConfirmed;
        this.createdAt = createdAt;
    }

    public AdminOrderResponse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getUserUid() {
        return userUid;
    }

    public void setUserUid(Integer userUid) {
        this.userUid = userUid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
