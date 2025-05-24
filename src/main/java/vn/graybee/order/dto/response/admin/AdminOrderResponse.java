package vn.graybee.order.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.order.enums.DeliveryType;
import vn.graybee.order.enums.OrderStatus;
import vn.graybee.order.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AdminOrderResponse {

    private long id;

    private String uid;

    private BigDecimal totalAmount;

    private String phoneNumber;

    private String fullName;

    private PaymentStatus paymentStatus;

    private DeliveryType deliveryType;

    private OrderStatus status;

    private boolean isCancelled;

    private boolean isConfirmed;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public AdminOrderResponse(long id, String uid, BigDecimal totalAmount, String phoneNumber, String fullName, PaymentStatus paymentStatus, DeliveryType deliveryType, OrderStatus status, boolean isCancelled, boolean isConfirmed, LocalDateTime createdAt) {
        this.id = id;
        this.uid = uid;
        this.totalAmount = totalAmount;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.paymentStatus = paymentStatus;
        this.deliveryType = deliveryType;
        this.status = status;
        this.isCancelled = isCancelled;
        this.isConfirmed = isConfirmed;
        this.createdAt = createdAt;
    }

    public AdminOrderResponse() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
