package vn.graybee.modules.cart.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.cart.dto.response.CartItemDto;
import vn.graybee.modules.cart.model.Cart;

import java.util.List;

public interface CartService {

    Cart findOrCreateCart(Long accountId, String sessionId);

    BasicMessageResponse<List<CartItemDto>> findCartByAccountIdOrSessionId(Long accountId, String sessionId);

    Cart getCartById(Integer cartId);

    void updateCartTotal(Integer cartId);

    void applyDiscount(Integer cartId, String discountCode);

    BasicMessageResponse<?> clearCartItems(Integer cartId);

    Integer getCartIdByAccountIdOrSessionId(Long accountId, String sessionId);

}
