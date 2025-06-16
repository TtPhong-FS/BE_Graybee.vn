package vn.graybee.modules.order.dto.request;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderCreateRequest {

    private List<Long> cartItemIds;

    private Long addressId;

    private String paymentMethod;

    @Valid
    private CustomerInfoRequest customerInfo;

    @Valid
    private ShippingInfoRequest shippingInfo;


}
