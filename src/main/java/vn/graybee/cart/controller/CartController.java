package vn.graybee.cart.controller;

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
import vn.graybee.account.security.UserDetail;
import vn.graybee.account.service.AccountService;
import vn.graybee.cart.dto.request.AddCartItemRequest;
import vn.graybee.cart.dto.request.DecreaseQuantityRequest;
import vn.graybee.cart.dto.response.CartItemBasicDto;
import vn.graybee.cart.dto.response.CartItemDto;
import vn.graybee.cart.service.CartItemService;
import vn.graybee.cart.service.CartService;
import vn.graybee.common.dto.BasicMessageResponse;

import java.util.List;

@RestController
@RequestMapping("{api.cart}")
public class CartController {

    private final CartService cartService;

    private final CartItemService cartItemService;

    private final AccountService accountService;

    public CartController(CartService cartService, CartItemService cartItemService, AccountService accountService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<CartItemDto>>> getAllCartByAccountIdOrSessionId(
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        Long accountId = null;
        if (userDetail != null && userDetail.getUser().getId() != null) {
            accountId = userDetail.getUser().getId();
        }
        return ResponseEntity.ok(cartService.findCartByUserUidOrSessionId(uid, sessionId));
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<CartItemDto>> add(
            @RequestBody @Valid AddCartItemRequest request,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = null;
        if (userDetail != null && userDetail.getUser().getId() != null) {
            accountId = userDetail.getUser().getId();
        }
        Integer cartId = cartService.findOrCreateCart(accountId, sessionId).getId();

        return ResponseEntity.ok(cartItemService.addItemToCart(cartId, request));
    }

    @PutMapping("/decrease")
    public ResponseEntity<BasicMessageResponse<CartItemBasicDto>> decreaseQuantityToCartItem(
            @RequestBody @Valid DecreaseQuantityRequest request,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = null;
        if (userDetail != null && userDetail.getUser().getId() != null) {
            accountId = userDetail.getUser().getId();
        }
        return ResponseEntity.ok(cartService.decreaseQuantityToCartItem(request, uid, sessionId));
    }

    @DeleteMapping("/cartItem/{cartItemId}")
    public ResponseEntity<BasicMessageResponse<Integer>> deleteItemToCart(
            @PathVariable("cartItemId") Integer cartItemId,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = null;
        if (userDetail != null && userDetail.getUser().getId() != null) {
            accountId = userDetail.getUser().getId();
        }
        return ResponseEntity.ok(cartService.deleteCartItemToCart(cartItemId, uid, sessionId));
    }

    @DeleteMapping
    public ResponseEntity<BasicMessageResponse<?>> clearItemsToCart(
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = null;
        if (userDetail != null && userDetail.getUser().getId() != null) {
            accountId = userDetail.getUser().getId();
        }
        return ResponseEntity.ok(cartService.clearItemsToCart(uid, sessionId));
    }


}
