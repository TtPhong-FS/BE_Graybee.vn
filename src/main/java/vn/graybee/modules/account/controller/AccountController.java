package vn.graybee.modules.account.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.account.dto.response.FavoriteProductResponse;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.account.service.FavouriteService;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.user.OrderHistoryResponse;
import vn.graybee.modules.order.service.OrderService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.privateApi.account}")
public class AccountController {

    private final FavouriteService favouriteService;

    private final OrderService orderService;

    @GetMapping("/favourites")
    public ResponseEntity<BasicMessageResponse<List<FavoriteProductResponse>>> getFavoriteProductByUserUid(@AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = userDetail.user().getId();
        return ResponseEntity.ok(favouriteService.getFavoriteProductByUserUid(accountId));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<BasicMessageResponse<?>> addFavoriteProduct(
            @PathVariable("productId") long productId,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        Long accountId = userDetail.user().getId();
        return ResponseEntity.ok(favouriteService.addFavoriteProduct(accountId, productId));
    }

    @GetMapping("/orders/history")
    public ResponseEntity<BasicMessageResponse<List<OrderHistoryResponse>>> getOrderHistoryByAccountId(@AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = userDetail.user().getId();

        List<OrderHistoryResponse> orders = orderService.getOrderHistoryByAccountId(accountId);

        final String msg = orders.isEmpty() ? "Bạn chưa có lịch sử đơn hàng nào" : "Lấy lịch sử đơn hàng thành công";
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        orders,
                        msg
                )
        );
    }

    @PutMapping("/orders/cancel/{code}")
    public ResponseEntity<BasicMessageResponse<CancelOrderResponse>> cancelOrderByCode(
            @PathVariable("code") String code) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        orderService.cancelOrderByCode(code),
                        "Huỷ đơn hàng thành công"
                )
        );
    }

}
