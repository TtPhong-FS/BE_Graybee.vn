package vn.graybee.controllers.admin.orders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.admin.orders.ConfirmOrderResponse;
import vn.graybee.services.orders.OrderService;
import vn.graybee.services.users.UserService;

@RestController
@RequestMapping("${api.orders}")
public class AdminOrderController {

    private final UserService userService;

    private final OrderService orderService;

    public AdminOrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PutMapping("/order/confirm/{id}")
    public ResponseEntity<BasicMessageResponse<ConfirmOrderResponse>> confirmOrderById(@PathVariable("id") long id) {
        return ResponseEntity.ok(orderService.confirmOrder(id));
    }

}
