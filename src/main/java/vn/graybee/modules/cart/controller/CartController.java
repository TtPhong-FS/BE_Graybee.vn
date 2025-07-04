package vn.graybee.modules.cart.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.cart.dto.response.CartItemDto;
import vn.graybee.modules.cart.service.CartItemService;
import vn.graybee.modules.cart.service.CartService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.carts}")
public class CartController {

    private final CartService cartService;

    private final CartItemService cartItemService;

    private final MessageSourceUtil messageSourceUtil;

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CartItemDto>>> getAllCartByAccountIdOrSessionId(
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        Long accountId = null;
        if (userDetail != null && userDetail.user().getId() != null) {
            accountId = userDetail.user().getId();
        }

        return ResponseEntity.ok(
                MessageBuilder.ok(
                        cartService.findCartByAccountIdOrSessionId(accountId, sessionId), null
                )
        );
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<BasicMessageResponse<CartItemDto>> addItemToCart(
            @PathVariable long productId,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = null;
        if (userDetail != null && userDetail.user().getId() != null) {
            accountId = userDetail.user().getId();
        }

        return ResponseEntity.ok(
                MessageBuilder.ok(
                        cartService.findOrCreateCartAfterAddItem(accountId, sessionId, productId),
                        messageSourceUtil.get("cart.item.success_add")
                )
        );
    }

    @PutMapping("/decrease/{productId}")
    public ResponseEntity<BasicMessageResponse<CartItemDto>> decreaseQuantityToCartItem(
            @PathVariable long productId,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = null;
        if (userDetail != null && userDetail.user().getId() != null) {
            accountId = userDetail.user().getId();
        }

        Long cartId = cartService.getCartIdByAccountIdOrSessionId(accountId, sessionId);
        cartService.updateCartTotal(cartId);
        return ResponseEntity.ok(
                MessageBuilder.ok(cartItemService.decreaseItemQuantity(cartId, productId), "Cập nhật số lượng thành công")
        );
    }

    @PutMapping("/quantity/{cartItemId}/{quantity}")
    public ResponseEntity<BasicMessageResponse<?>> updateItemQuantity(
            @PathVariable int quantity,
            @PathVariable long cartItemId,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {

        Long accountId = null;
        if (userDetail != null && userDetail.user().getId() != null) {
            accountId = userDetail.user().getId();
        }

        Long cartId = cartService.getCartIdByAccountIdOrSessionId(accountId, sessionId);

        if (quantity == 0) {
            cartItemService.removeItemFromCart(cartId, cartItemId);
            return ResponseEntity.ok(
                    MessageBuilder.ok(
                            cartItemId, "Sản phẩm đã được xoá khỏi giỏ hàng"
                    ));
        }

        CartItemDto cartItemDto = cartItemService.updateItemQuantity(cartItemId, quantity);
        cartService.updateCartTotal(cartId);
        return ResponseEntity.ok(
                MessageBuilder.ok(cartItemDto, "Cập nhật số lượng thành công")
        );
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<BasicMessageResponse<Long>> deleteItemToCart(
            @PathVariable("cartItemId") Long cartItemId,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = null;
        if (userDetail != null && userDetail.user().getId() != null) {
            accountId = userDetail.user().getId();
        }

        Long cartId = cartService.getCartIdByAccountIdOrSessionId(accountId, sessionId);

        cartService.updateCartTotal(cartId);

        return ResponseEntity.ok(
                MessageBuilder.ok(cartItemService.removeItemFromCart(cartId, cartItemId),
                        messageSourceUtil.get("cart.item.success_remove"))
        );
    }

    @DeleteMapping("/clear-items")
    public ResponseEntity<BasicMessageResponse<?>> clearItemsToCart(
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = null;
        if (userDetail != null && userDetail.user().getId() != null) {
            accountId = userDetail.user().getId();
        }

        Long cartId = cartService.getCartIdByAccountIdOrSessionId(accountId, sessionId);

        cartService.clearCartItems(cartId);

        return ResponseEntity.ok(
                MessageBuilder.ok(null,
                        messageSourceUtil.get("cart.item.success_clear_all"))
        );
    }

}
