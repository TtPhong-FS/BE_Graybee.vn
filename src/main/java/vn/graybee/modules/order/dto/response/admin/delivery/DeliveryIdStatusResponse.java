package vn.graybee.modules.order.dto.response.admin.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.order.enums.DeliveryStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryIdStatusResponse {

    private long id;

    private DeliveryStatus status;

}
