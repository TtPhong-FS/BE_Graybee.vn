package vn.graybee.modules.order.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.order.enums.DeliveryType;
import vn.graybee.modules.order.enums.OrderStatus;
import vn.graybee.modules.order.enums.PaymentStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderResponse {

    private long orderId;

    private double total;

    private String recipientName;

    private String recipientPhone;

    private PaymentStatus paymentStatus;

    private DeliveryType deliveryType;

    private OrderStatus orderStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderAt;

}
