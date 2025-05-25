package vn.graybee.cart.service.impl;

import org.springframework.stereotype.Service;
import vn.graybee.cart.model.Cart;
import vn.graybee.cart.repository.CartRepository;
import vn.graybee.cart.service.CartItemService;
import vn.graybee.cart.service.CartService;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;

import java.math.BigDecimal;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemService cartItemService;

    private final MessageSourceUtil messageSourceUtil;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService, MessageSourceUtil messageSourceUtil) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.messageSourceUtil = messageSourceUtil;
    }

    @Override
    public Cart findOrCreateCart(Long accountId, String sessionId) {
        if (accountId == null && sessionId == null) {
            throw new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("common.not_found", new Object[]{messageSourceUtil.get("cart.name")}));
        }

        Cart cart;

        if (accountId != null) {
            cart = cartRepository.findByAccountId(accountId).orElseGet(Cart::new);
            cart.setAccountId(accountId);
            cart.setSessionId(null);
        } else {
            cart = cartRepository.findBySessionId(sessionId).orElseGet(Cart::new);
            cart.setSessionId(sessionId);
            cart.setAccountId(null);
        }

        return cartRepository.save(cart);

    }

    @Override
    public Cart findCartByAccountIdOrSessionId(Long accountId, String sessionId) {
        Cart cart;
        if (accountId != null) {
            cart = cartRepository.findByAccountId(accountId)
                    .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("common.not.found", new Object[]{messageSourceUtil.get("cart.name")})));
        } else {
            cart = cartRepository.findBySessionId(sessionId).orElseThrow(() ->
                    new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("common.not.found", new Object[]{messageSourceUtil.get("cart.name")})));
        }
        return cart;
    }


    @Override
    public Cart getCartById(Integer cartId) {
        return null;
    }

    @Override
    public void updateCartTotal(Integer cartId) {
        BigDecimal totalAmount = cartItemService.getCartItemTotals(cartId);
        cartRepository.updateCartTotal(cartId, totalAmount);
    }

    @Override
    public void applyDiscount(Integer cartId, String discountCode) {

    }

    @Override
    public void clearCartItems(Integer cartId) {
        checkExistsById(cartId);
        cartItemService.clearCartItems(cartId);
        cartRepository.updateCartTotal(cartId, BigDecimal.ZERO);
    }

    @Override
    public Integer getCartIdByAccountIdOrSessionId(Long accountId, String sessionId) {
        return cartRepository.findIdByAccountIdOrSessionId(accountId, sessionId)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("cart.not.found")));
    }

    private void checkExistsById(Integer cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("common.not.found", new Object[]{messageSourceUtil.get("cart.name")}));
        }
    }

}
