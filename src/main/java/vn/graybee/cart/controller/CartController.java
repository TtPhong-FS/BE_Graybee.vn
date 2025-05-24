//package vn.graybee.cart.controller;
//
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.CookieValue;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import vn.graybee.cart.dto.request.AddCartItemRequest;
//import vn.graybee.cart.dto.request.RemoveCartItemRequest;
//import vn.graybee.cart.dto.response.CartItemBasicDto;
//import vn.graybee.cart.dto.response.CartItemDto;
//import vn.graybee.cart.service.CartService;
//import vn.graybee.common.dto.BasicMessageResponse;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("{api.cart}")
//public class CartController {
//
//    private final CartService cartService;
//
//    public CartController(CartService cartService) {
//        this.cartService = cartService;
//    }
//
//    @GetMapping
//    public ResponseEntity<BasicMessageResponse<List<CartItemDto>>> findCartByUserUid(
//            @CookieValue(value = "sessionId", required = false) String sessionId,
//            @AuthenticationPrincipal UserPrincipal userPrincipal
//    ) {
//        String uid = null;
//        if (userPrincipal != null && userPrincipal.getUser().getUserUid() != null) {
//            uid = userPrincipal.getUser().getUserUid();
//        }
//        return ResponseEntity.ok(cartService.findCartByUserUidOrSessionId(uid, sessionId));
//    }
//
//    @PostMapping
//    public ResponseEntity<BasicMessageResponse<CartItemDto>> createCart(
//            @RequestBody @Valid AddCartItemRequest request,
//            @CookieValue(value = "sessionId", required = false) String sessionId,
//            @AuthenticationPrincipal UserPrincipal userPrincipal) {
//        String uid = null;
//        if (userPrincipal != null && userPrincipal.getUser().getUserUid() != null) {
//            uid = userPrincipal.getUser().getUserUid();
//        }
//        return ResponseEntity.ok(cartService.addItemToCart(request, uid, sessionId));
//    }
//
//    @PutMapping("/decrease")
//    public ResponseEntity<BasicMessageResponse<CartItemBasicDto>> decreaseQuantityToCartItem(
//            @RequestBody @Valid RemoveCartItemRequest request,
//            @CookieValue(value = "sessionId", required = false) String sessionId,
//            @AuthenticationPrincipal UserPrincipal userPrincipal) {
//        String uid = null;
//        if (userPrincipal != null && userPrincipal.getUser().getUserUid() != null) {
//            uid = userPrincipal.getUser().getUserUid();
//        }
//        return ResponseEntity.ok(cartService.decreaseQuantityToCartItem(request, uid, sessionId));
//    }
//
//    @DeleteMapping("/cartItem/{cartItemId}")
//    public ResponseEntity<BasicMessageResponse<Integer>> deleteItemToCart(
//            @PathVariable("cartItemId") Integer cartItemId,
//            @CookieValue(value = "sessionId", required = false) String sessionId,
//            @AuthenticationPrincipal UserPrincipal userPrincipal) {
//        String uid = null;
//        if (userPrincipal != null && userPrincipal.getUser().getUserUid() != null) {
//            uid = userPrincipal.getUser().getUserUid();
//        }
//        return ResponseEntity.ok(cartService.deleteCartItemToCart(cartItemId, uid, sessionId));
//    }
//
//    @DeleteMapping
//    public ResponseEntity<BasicMessageResponse<?>> clearItemsToCart(
//            @CookieValue(value = "sessionId", required = false) String sessionId,
//            @AuthenticationPrincipal UserPrincipal userPrincipal) {
//        String uid = null;
//        if (userPrincipal != null && userPrincipal.getUser().getUserUid() != null) {
//            uid = userPrincipal.getUser().getUserUid();
//        }
//        return ResponseEntity.ok(cartService.clearItemsToCart(uid, sessionId));
//    }
//
//
//}
