package vn.graybee.modules.cart.service;

import vn.graybee.modules.cart.dto.request.AddCartItemRequest;
import vn.graybee.modules.cart.dto.response.CartItemDto;
import vn.graybee.modules.cart.model.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemService {

    CartItemDto addItemToCart(Integer cartId, AddCartItemRequest request);

    CartItemDto decreaseItemQuantity(Integer cartId, Long productId, int decreaseQuantity);

    Integer removeItemFromCart(Integer cartId, Integer cartItemId);

    List<CartItemDto> getCartItemsByCartId(Integer cartId);

    List<CartItem> getCartItemByIdsAndCartId(List<Integer> cartItemIds, Integer cartId);

    void clearCartItems(Integer cartId);

    BigDecimal getCartItemTotals(Integer cartId);

    List<Integer> clearCartItemsByIds(List<Integer> cartItemIds, Integer cartId);

}
