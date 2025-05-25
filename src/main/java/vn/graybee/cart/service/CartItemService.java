package vn.graybee.cart.service;

import vn.graybee.cart.dto.request.AddCartItemRequest;
import vn.graybee.cart.dto.response.CartItemDto;
import vn.graybee.cart.model.CartItem;
import vn.graybee.common.dto.BasicMessageResponse;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemService {

    BasicMessageResponse<CartItemDto> addItemToCart(Integer cartId, AddCartItemRequest request);

    CartItemDto decreaseItemQuantity(Integer cartId, Long productId, int decreaseQuantity);

    Integer removeItemFromCart(Integer cartId, Integer cartItemId);

    List<CartItemDto> getCartItems(Integer cartId);

    List<CartItem> getCartItemByIdsAndCartId(List<Integer> cartItemIds, Integer cartId);

    void clearCartItems(Integer cartId);

    BigDecimal getCartItemTotals(Integer cartId);

    List<Integer> clearCartItemsByIds(List<Integer> cartItemIds, Integer cartId);

}
