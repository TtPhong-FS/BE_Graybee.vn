package vn.graybee.cart.service;

import vn.graybee.cart.model.Cart;

public interface CartService {

    Cart findOrCreateCart(Long accountId, String sessionId);

    Cart findCartByAccountIdOrSessionId(Long accountId, String sessionId);

    Cart getCartById(Integer cartId);

    void updateCartTotal(Integer cartId);

    void applyDiscount(Integer cartId, String discountCode);

    void clearCartItems(Integer cartId);

    Integer getCartIdByAccountIdOrSessionId(Long accountId, String sessionId);

}
