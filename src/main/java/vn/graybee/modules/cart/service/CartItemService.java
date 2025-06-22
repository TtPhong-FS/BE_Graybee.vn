package vn.graybee.modules.cart.service;

import vn.graybee.modules.cart.dto.response.CartItemDto;
import vn.graybee.modules.cart.model.CartItem;

import java.util.List;

public interface CartItemService {

    CartItemDto addItemToCart(Long cartId, long productId);

    CartItemDto decreaseItemQuantity(Long cartId, long productId);

    Long removeItemFromCart(Long cartId, Long cartItemId);

    List<CartItemDto> getCartItemsByCartId(Long cartId);

    List<CartItem> getCartItemByIdsAndCartId(List<Long> cartItemIds, Long cartId);

    void clearCartItems(Long cartId);

    double getCartItemTotals(Long cartId);

    List<Long> clearCartItemsByIds(List<Long> cartItemIds, Long cartId);

    CartItemDto updateItemQuantity(long cartItemId, int quantity);

}
