package vn.graybee.modules.cart.service;

import vn.graybee.modules.cart.dto.response.CartItemDto;

import java.util.List;

public interface CartService {

    CartItemDto findOrCreateCartAfterAddItem(Long accountId, String sessionId, long productId);

    void syncGuestCartToAccount(Long accountId, String sessionId);

    List<CartItemDto> findCartByAccountIdOrSessionId(Long accountId, String sessionId);

    void updateCartTotal(Long cartId);

    void clearCartItems(Long cartId);

    Long getCartIdByAccountIdOrSessionId(Long accountId, String sessionId);

}
