package vn.graybee.modules.cart.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.cart.dto.request.AddCartItemRequest;
import vn.graybee.modules.cart.dto.request.DecreaseQuantityRequest;
import vn.graybee.modules.cart.dto.response.CartItemDto;
import vn.graybee.modules.cart.service.CartItemService;
import vn.graybee.modules.cart.service.CartService;

import java.util.List;

@RestController
@RequestMapping("${api.publicApi.carts}")
public class CartController {

    private final CartService cartService;

    private final CartItemService cartItemService;

    public CartController(CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CartItemDto>>> getAllCartByAccountIdOrSessionId(
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        Long accountId = null;
        if (userDetail != null && userDetail.user().getId() != null) {
            accountId = userDetail.user().getId();
        }
        return ResponseEntity.ok(cartService.findCartByAccountIdOrSessionId(accountId, sessionId));
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<CartItemDto>> addItemToCart(
            @RequestBody @Valid AddCartItemRequest request,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = null;
        if (userDetail != null && userDetail.user().getId() != null) {
            accountId = userDetail.user().getId();
        }
        Integer cartId = cartService.findOrCreateCart(accountId, sessionId).getId();

        return ResponseEntity.ok(cartItemService.addItemToCart(cartId, request));
    }

    @PutMapping("/decrease")
    public ResponseEntity<BasicMessageResponse<CartItemDto>> decreaseQuantityToCartItem(
            @RequestBody @Valid DecreaseQuantityRequest request,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = null;
        if (userDetail != null && userDetail.user().getId() != null) {
            accountId = userDetail.user().getId();
        }

        Integer cartId = cartService.findOrCreateCart(accountId, sessionId).getId();

        return ResponseEntity.ok(cartItemService.decreaseItemQuantity(cartId, request.getProductId(), request.getQuantity()));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<BasicMessageResponse<Integer>> deleteItemToCart(
            @PathVariable("cartItemId") Integer cartItemId,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = null;
        if (userDetail != null && userDetail.user().getId() != null) {
            accountId = userDetail.user().getId();
        }

        Integer cartId = cartService.findOrCreateCart(accountId, sessionId).getId();

        return ResponseEntity.ok(cartItemService.removeItemFromCart(cartId, cartItemId));
    }

    @DeleteMapping("/clear-items")
    public ResponseEntity<BasicMessageResponse<?>> clearItemsToCart(
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = null;
        if (userDetail != null && userDetail.user().getId() != null) {
            accountId = userDetail.user().getId();
        }
        Integer cartId = cartService.findOrCreateCart(accountId, sessionId).getId();
        return ResponseEntity.ok(cartService.clearCartItems(cartId));
    }

}
