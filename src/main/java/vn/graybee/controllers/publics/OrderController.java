package vn.graybee.controllers.publics;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.UserPrincipal;
import vn.graybee.requests.orders.OrderCreateRequest;
import vn.graybee.response.users.AddressExistingDto;
import vn.graybee.services.orders.OrderService;
import vn.graybee.services.users.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/order")
public class OrderController {

    private final OrderService orderService;

    private final AddressService addressService;

    public OrderController(OrderService orderService, AddressService addressService) {
        this.orderService = orderService;
        this.addressService = addressService;
    }

    @PostMapping("/create")
    public ResponseEntity<BasicMessageResponse<?>> createOrder(
            @RequestBody @Valid OrderCreateRequest request,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer uid = null;
        if (userPrincipal != null && userPrincipal.getUser().getUserUid() != null) {
            uid = userPrincipal.getUser().getUserUid();
        }
        return ResponseEntity.ok(orderService.createOrder(request, uid, sessionId));
    }

    @GetMapping("/address-existing")
    public ResponseEntity<BasicMessageResponse<List<AddressExistingDto>>> getAddressExistingByUserUidOrSessionId(
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Integer uid = null;
        if (userPrincipal != null && userPrincipal.getUser().getUserUid() != null) {
            uid = userPrincipal.getUser().getUserUid();
        }
        return ResponseEntity.ok(addressService.getAddressExistingByUserUidOrSessionId(uid, sessionId));
    }

}
