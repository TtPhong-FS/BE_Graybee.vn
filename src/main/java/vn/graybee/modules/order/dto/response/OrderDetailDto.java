package vn.graybee.modules.order.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.order.enums.OrderStatus;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class OrderDetailDto {

    private String orderCode;

    private List<OrderItemDto> orderItems;

    private OrderStatus status;

    private double totalAmount;

    private DeliveryDto delivery;

    private PaymentDto payment;

    public OrderDetailDto(String orderCode, OrderStatus status, double totalAmount) {
        this.orderCode = orderCode;
        this.status = status;
        this.totalAmount = totalAmount;
    }

}
