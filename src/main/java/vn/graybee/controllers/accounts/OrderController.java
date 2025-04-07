package vn.graybee.controllers.accounts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.orders.OrderHistoryResponse;
import vn.graybee.services.orders.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/history")
    public ResponseEntity<BasicMessageResponse<List<OrderHistoryResponse>>> getOrdersByUserUid(@RequestHeader("Authorization") Integer userUid) {
        return ResponseEntity.ok(orderService.findOrderHistoriesByUserUid(userUid));
    }

    @GetMapping("/history/status")
    public ResponseEntity<BasicMessageResponse<List<OrderHistoryResponse>>> findOrderHistoryByUserUidAndStatus(@RequestHeader("Authorization") Integer userUid, @RequestParam("status") String status) {
        return ResponseEntity.ok(orderService.findOrderHistoryByUserUidAndStatus(userUid, status));
    }

}
