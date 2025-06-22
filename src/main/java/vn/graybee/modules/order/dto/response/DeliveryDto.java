package vn.graybee.modules.order.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.order.enums.DeliveryStatus;
import vn.graybee.modules.order.enums.DeliveryType;
import vn.graybee.modules.order.enums.ShippingMethod;
import vn.graybee.modules.order.model.Delivery;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor

public class DeliveryDto {

    private ShippingMethod shippingMethod;

    private DeliveryType deliveryType;

    private DeliveryStatus status;

    private String recipientName;

    private String recipientPhone;

    private String shippingAddress;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate estimatedDeliveryDate;

    public DeliveryDto(Delivery delivery) {
        this.shippingMethod = delivery.getShippingMethod();
        this.deliveryType = delivery.getDeliveryType();
        this.status = delivery.getStatus();
        this.recipientName = delivery.getRecipientName();
        this.recipientPhone = delivery.getRecipientPhone();
        this.shippingAddress = delivery.getShippingAddress();
        this.estimatedDeliveryDate = delivery.getEstimatedDeliveryDate();
    }

}
