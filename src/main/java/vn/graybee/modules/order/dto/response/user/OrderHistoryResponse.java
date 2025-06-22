package vn.graybee.modules.order.dto.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.order.enums.DeliveryType;
import vn.graybee.modules.order.enums.OrderStatus;
import vn.graybee.modules.order.enums.PaymentMethod;
import vn.graybee.modules.order.enums.PaymentStatus;
import vn.graybee.modules.order.enums.ShippingMethod;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class OrderHistoryResponse {

    private String code;

    private OrderStatus status;

    private double totalAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    private DeliveryType deliveryType;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private ShippingMethod shippingMethod;

    public OrderHistoryResponse(String code, OrderStatus status, double totalAmount, LocalDateTime orderDate, DeliveryType deliveryType, PaymentMethod paymentMethod, PaymentStatus paymentStatus, ShippingMethod shippingMethod) {
        this.code = code;
        this.status = status;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.deliveryType = deliveryType;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.shippingMethod = shippingMethod;
    }

}
