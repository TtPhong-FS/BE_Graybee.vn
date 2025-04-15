package vn.graybee.response.orders;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderBasicDto {

    private long id;

    private OrderStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    public OrderBasicDto() {
    }

    public OrderBasicDto(long id, OrderStatus status, LocalDateTime orderDate, BigDecimal totalAmount) {
        this.id = id;
        this.status = status;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

}
