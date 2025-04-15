package vn.graybee.controllers.publics;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.orders.OrderCreateRequest;
import vn.graybee.services.orders.OrderService;
import vn.graybee.services.users.UserService;

@RestController
@RequestMapping("/api/v1/public/order")
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<BasicMessageResponse<?>> createOrder(@RequestBody @Valid OrderCreateRequest request, @CookieValue(value = "sessionId", required = false) String sessionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Integer uid = null;
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            uid = userService.getUidByUsername(auth.getName());
        }
        return ResponseEntity.ok(orderService.createOrder(request, uid, sessionId));
    }


}
