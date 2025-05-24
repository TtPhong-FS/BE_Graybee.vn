package vn.graybee.order.dto.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.order.enums.DeliveryType;
import vn.graybee.order.enums.OrderStatus;
import vn.graybee.order.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class OrderHistoryResponse {

    private long orderId;

    private OrderStatus status;

    private BigDecimal totalAmount;

    private int totalQuantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    private List<OrderDetailProductDto> orderDetails;

    private DeliveryType deliveryType;

    private PaymentMethod paymentMethod;

    public OrderHistoryResponse() {
    }

    public OrderHistoryResponse(long orderId, OrderStatus status, BigDecimal totalAmount, int totalQuantity, LocalDateTime orderDate, List<OrderDetailProductDto> orderDetails, DeliveryType deliveryType, PaymentMethod paymentMethod) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
        this.deliveryType = deliveryType;
        this.paymentMethod = paymentMethod;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
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

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
