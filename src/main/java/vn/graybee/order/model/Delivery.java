package vn.graybee.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import vn.graybee.order.enums.DeliveryStatus;
import vn.graybee.order.enums.DeliveryType;
import vn.graybee.order.enums.ShippingMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "tracking_number", length = 50)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "shipping_method")
    private ShippingMethod shippingMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "delivery_type")
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "estimated_delivery_date")
    private LocalDate estimatedDeliveryDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Delivery() {
    }

    public Delivery(LocalDate orderDate) {
        this.orderDate = orderDate;
        setEstimatedDeliveryDate();
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setEstimatedDeliveryDate() {
        int daysToAdd = ThreadLocalRandom.current().nextInt(2, 4);
        this.estimatedDeliveryDate = this.orderDate.plusDays(daysToAdd);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public LocalDate getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

}
