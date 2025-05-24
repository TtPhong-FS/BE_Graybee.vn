package vn.graybee.cart.service;

import vn.graybee.cart.dto.request.AddCartItemRequest;
import vn.graybee.cart.dto.request.RemoveCartItemRequest;
import vn.graybee.cart.dto.response.CartItemBasicDto;
import vn.graybee.cart.dto.response.CartItemDto;
import vn.graybee.cart.entity.Cart;
import vn.graybee.common.dto.BasicMessageResponse;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    BasicMessageResponse<CartItemDto> addItemToCart(AddCartItemRequest request, String uid, String sessionId);

    CartItemBasicDto updateCartItemQuantity(int cartItemId, int quantity, BigDecimal price);

    BasicMessageResponse<Cart> findById(int id);

    BasicMessageResponse<List<CartItemDto>> findCartByUserUidOrSessionId(String userUid, String sessionId);

    BasicMessageResponse<?> clearItemsToCart(String userUid, String sessionId);

    BasicMessageResponse<Integer> deleteCartItemToCart(int cartItemId, String userUid, String sessionId);

    BasicMessageResponse<CartItemBasicDto> decreaseQuantityToCartItem(RemoveCartItemRequest request, String userUid, String sessionId);

    void updateTotalAmount(int cartId);

    Integer findByUserIdOrSessionId(String userUid, String sessionId);

}
