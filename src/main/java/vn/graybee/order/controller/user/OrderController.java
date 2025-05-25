package vn.graybee.order.controller.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.account.security.UserDetail;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.order.dto.request.OrderCreateRequest;
import vn.graybee.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.order.dto.response.user.OrderHistoryResponse;
import vn.graybee.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<?>> createOrder(
            @RequestBody @Valid OrderCreateRequest request,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long account = null;
        if (userDetail != null && userDetail.getUser().getUid() != null) {
            account = userDetail.getUser().getId();
        }
        return ResponseEntity.ok(orderService.createOrder(request, account, sessionId));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BasicMessageResponse<CancelOrderResponse>> cancelOrderById(
            @PathVariable("id") long id) {
        return ResponseEntity.ok(orderService.cancelOrderById(id));
    }

    @GetMapping("/orders-history/{status}")
    public ResponseEntity<BasicMessageResponse<List<OrderHistoryResponse>>> getOrderHistoryByAccountId(@PathVariable(value = "status", required = false) String status, @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = userDetail.getUser().getId();
        return ResponseEntity.ok(orderService.getOrderHistoryByAccountId(accountId, status));
    }

}
