package vn.graybee.modules.order.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.order.dto.response.admin.delivery.DeliveryIdStatusResponse;
import vn.graybee.modules.order.model.Delivery;
import vn.graybee.modules.order.service.DeliveryService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.admin.deliveries}")
public class AdminDeliveryController {

    private final DeliveryService deliveryService;

    private final MessageSourceUtil messageSourceUtil;

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<Delivery>>> getAllDelivery() {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        deliveryService.getAllDelivery(),
                        messageSourceUtil.get("order.delivery.success.fetch.list")
                )
        );
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BasicMessageResponse<DeliveryIdStatusResponse>> updateDeliveryStatus(
            @PathVariable long id,
            @PathVariable String status
    ) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        deliveryService.updateDeliveryStatusById(id, status),
                        messageSourceUtil.get("order.delivery.success.update.status")
                )
        );
    }

}
