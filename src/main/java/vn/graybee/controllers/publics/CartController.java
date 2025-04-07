package vn.graybee.controllers.publics;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.orders.AddCartItemRequest;
import vn.graybee.requests.orders.RemoveCartItemRequest;
import vn.graybee.response.publics.carts.AddItemToCartResponse;
import vn.graybee.response.publics.carts.DecreaseQuantityResponse;
import vn.graybee.services.orders.CartService;
import vn.graybee.services.users.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/carts")
public class CartController {

    private final CartService cartService;

    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<AddItemToCartResponse>>> findCartByUserUid(@RequestHeader(value = "Authorization", required = false) String token, @CookieValue(value = "sessionId", required = false) String sessionId) {
        Integer uid = null;
        if (token != null && !token.isEmpty()) {
            String subToken = token.substring(7);
            uid = userService.getUidByToken(subToken);
        }
        return ResponseEntity.ok(cartService.findCartByUserUidOrSessionId(uid, sessionId));
    }

    @PostMapping("/add")
    public ResponseEntity<BasicMessageResponse<AddItemToCartResponse>> addItemToCart(@RequestBody @Valid AddCartItemRequest request, @RequestHeader(value = "Authorization", required = false) String token, @CookieValue(value = "sessionId", required = false) String sessionId) {
        Integer uid = null;
        if (token != null && !token.isEmpty()) {
            String subToken = token.substring(7);
            uid = userService.getUidByToken(subToken);
        }
        return ResponseEntity.ok(cartService.addItemToCart(request, uid, sessionId));
    }

    @PutMapping("/item/decrease")
    public ResponseEntity<BasicMessageResponse<DecreaseQuantityResponse>> decreaseQuantityToCartItem(@RequestBody @Valid RemoveCartItemRequest request, @RequestHeader(value = "Authorization", required = false) String token, @CookieValue(value = "sessionId", required = false) String sessionId) {
        Integer uid = null;
        if (token != null && !token.isEmpty()) {
            String subToken = token.substring(7);
            uid = userService.getUidByToken(subToken);
        }
        return ResponseEntity.ok(cartService.decreaseQuantityToCartItem(request, uid, sessionId));
    }

    @DeleteMapping("/item/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> deleteItemToCart(@RequestParam("cartItemId") int cartItemId, @RequestHeader(value = "Authorization", required = false) String token, @CookieValue(value = "sessionId", required = false) String sessionId) {
        Integer uid = null;
        if (token != null && !token.isEmpty()) {
            String subToken = token.substring(7);
            uid = userService.getUidByToken(subToken);
        }
        return ResponseEntity.ok(cartService.deleteCartItemToCart(cartItemId, uid, sessionId));
    }

    @DeleteMapping
    public ResponseEntity<BasicMessageResponse<?>> clearItemsToCart(@RequestHeader(value = "Authorization", required = false) String token, @CookieValue(value = "sessionId", required = false) String sessionId) {
        Integer uid = null;
        if (token != null && !token.isEmpty()) {
            String subToken = token.substring(7);
            uid = userService.getUidByToken(subToken);
        }
        return ResponseEntity.ok(cartService.clearItemsToCart(uid, sessionId));
    }


}
