package vn.graybee.response.orders;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class OrderHistoryResponse {

    private long orderId;

    private String status;

    private BigDecimal totalAmount;

    private int totalQuantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    private List<OrderDetailProductDto> orderDetails;

    private String deliveryType;

    private String paymentMethod;

    public OrderHistoryResponse() {
    }

    public OrderHistoryResponse(long orderId, String status, BigDecimal totalAmount, int totalQuantity, LocalDateTime orderDate, List<OrderDetailProductDto> orderDetails, String deliveryType, String paymentMethod) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
        this.deliveryType = deliveryType;
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }


    public List<OrderDetailProductDto> getOrderDetails() {
        return orderDetails != null ? orderDetails : Collections.emptyList();
    }

    public void setOrderDetails(List<OrderDetailProductDto> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

}
