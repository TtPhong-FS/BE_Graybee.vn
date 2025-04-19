package vn.graybee.controllers.publics;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.UserPrincipal;
import vn.graybee.requests.orders.AddCartItemRequest;
import vn.graybee.requests.orders.RemoveCartItemRequest;
import vn.graybee.response.publics.carts.AddItemToCartResponse;
import vn.graybee.response.publics.carts.DecreaseQuantityResponse;
import vn.graybee.services.orders.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<AddItemToCartResponse>>> findCartByUserUid(
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Integer uid = null;
        if (userPrincipal != null && userPrincipal.getUser().getUserUid() != null) {
            uid = userPrincipal.getUser().getUserUid();
        }
        return ResponseEntity.ok(cartService.findCartByUserUidOrSessionId(uid, sessionId));
    }

    @PostMapping("/add")
    public ResponseEntity<BasicMessageResponse<AddItemToCartResponse>> addItemToCart(
            @RequestBody @Valid AddCartItemRequest request,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer uid = null;
        if (userPrincipal != null && userPrincipal.getUser().getUserUid() != null) {
            uid = userPrincipal.getUser().getUserUid();
        }
        return ResponseEntity.ok(cartService.addItemToCart(request, uid, sessionId));
    }

    @PutMapping("/item/decrease")
    public ResponseEntity<BasicMessageResponse<DecreaseQuantityResponse>> decreaseQuantityToCartItem(
            @RequestBody @Valid RemoveCartItemRequest request,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer uid = null;
        if (userPrincipal != null && userPrincipal.getUser().getUserUid() != null) {
            uid = userPrincipal.getUser().getUserUid();
        }
        return ResponseEntity.ok(cartService.decreaseQuantityToCartItem(request, uid, sessionId));
    }

    @DeleteMapping("/item/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> deleteItemToCart(
            @RequestParam("cartItemId") int cartItemId,
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer uid = null;
        if (userPrincipal != null && userPrincipal.getUser().getUserUid() != null) {
            uid = userPrincipal.getUser().getUserUid();
        }
        return ResponseEntity.ok(cartService.deleteCartItemToCart(cartItemId, uid, sessionId));
    }

    @DeleteMapping
    public ResponseEntity<BasicMessageResponse<?>> clearItemsToCart(
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer uid = null;
        if (userPrincipal != null && userPrincipal.getUser().getUserUid() != null) {
            uid = userPrincipal.getUser().getUserUid();
        }
        return ResponseEntity.ok(cartService.clearItemsToCart(uid, sessionId));
    }


}
