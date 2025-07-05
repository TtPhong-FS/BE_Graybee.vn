package vn.graybee.modules.order.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.order.dto.request.OrderCreateRequest;
import vn.graybee.modules.order.service.OrderService;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.orders}")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<BasicMessageResponse<?>> createOrder(
            @RequestBody @Valid OrderCreateRequest request,
            @RequestHeader(value = "x-session-id", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long account = null;
        if (userDetail != null && userDetail.user().getUid() != null) {
            account = userDetail.user().getId();
        }

        return ResponseEntity.ok(
                MessageBuilder.ok(
                        orderService.createOrder(request, account, sessionId),
                        "Đặt hàng thành công"
                )
        );
    }

}
