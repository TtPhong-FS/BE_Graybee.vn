package vn.graybee.services.orders;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.orders.Cart;
import vn.graybee.requests.orders.AddCartItemRequest;
import vn.graybee.requests.orders.RemoveCartItemRequest;
import vn.graybee.response.publics.carts.AddItemToCartResponse;
import vn.graybee.response.publics.carts.CartItemResponse;
import vn.graybee.response.publics.carts.DecreaseQuantityResponse;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    BasicMessageResponse<AddItemToCartResponse> addItemToCart(AddCartItemRequest request, Integer userUid, String sessionId);

    CartItemResponse updateCartItemQuantity(int cartItemId, int quantity, BigDecimal price);

    BasicMessageResponse<Cart> findById(int id);

    BasicMessageResponse<List<AddItemToCartResponse>> findCartByUserUidOrSessionId(Integer userUid, String sessionId);

    BasicMessageResponse<?> clearItemsToCart(Integer userUid, String sessionId);

    BasicMessageResponse<Integer> deleteCartItemToCart(int cartItemId, Integer userUid, String sessionId);

    BasicMessageResponse<DecreaseQuantityResponse> decreaseQuantityToCartItem(RemoveCartItemRequest request, Integer userUid, String sessionId);

}
