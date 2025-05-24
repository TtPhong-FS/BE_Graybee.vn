package vn.graybee.order.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.order.dto.response.admin.ConfirmOrderResponse;
import vn.graybee.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("${api.orders}")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<BasicMessageResponse<ConfirmOrderResponse>> confirmOrderById(@PathVariable("id") long id) {
        return ResponseEntity.ok(orderService.confirmOrder(id));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<BasicMessageResponse<CancelOrderResponse>> cancelOrderById(@PathVariable("id") long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @GetMapping
    public ResponseEntity<MessageResponse<List<AdminOrderResponse>>> fetchAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order
    ) {

        return ResponseEntity.ok(orderService.findAll(page, size, sortBy, order));
    }

}
