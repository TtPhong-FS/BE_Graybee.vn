package vn.graybee.modules.cart.service;

import vn.graybee.modules.cart.dto.request.AddCartItemRequest;
import vn.graybee.modules.cart.dto.response.CartItemDto;

import java.util.List;

public interface CartService {

    CartItemDto findOrCreateCartAfterAddItem(Long accountId, String sessionId, AddCartItemRequest request);

    void syncGuestCartToAccount(Long accountId, String sessionId);

    List<CartItemDto> findCartByAccountIdOrSessionId(Long accountId, String sessionId);

    void updateCartTotal(Integer cartId);

    void applyDiscount(Integer cartId, String discountCode);

    void clearCartItems(Integer cartId);

    Integer getCartIdByAccountIdOrSessionId(Long accountId, String sessionId);

}
