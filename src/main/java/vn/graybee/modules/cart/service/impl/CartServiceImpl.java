package vn.graybee.modules.cart.service.impl;

import org.springframework.stereotype.Service;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.cart.dto.response.CartItemDto;
import vn.graybee.modules.cart.model.Cart;
import vn.graybee.modules.cart.repository.CartRepository;
import vn.graybee.modules.cart.service.CartItemService;
import vn.graybee.modules.cart.service.CartService;

import java.math.BigDecimal;
import java.util.List;

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

        cart = cartRepository.findByAccountIdAndSessionId(accountId, sessionId)
                .orElseGet(Cart::new);

        boolean isNewCart = cart.getId() == null;

        if (isNewCart) {
            if (accountId != null) {
                cart.setAccountId(accountId);
                cart.setSessionId(null);
            } else {
                cart.setSessionId(sessionId);
                cart.setAccountId(null);
            }
            cart.setTotalAmount(BigDecimal.ZERO);

            cartRepository.save(cart);
        }

        return cart;

    }

    @Override
    public BasicMessageResponse<List<CartItemDto>> findCartByAccountIdOrSessionId(Long accountId, String sessionId) {
        Cart cart;

        cart = cartRepository.findByAccountIdAndSessionId(accountId, sessionId)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("cart.not.found")));

        List<CartItemDto> cartItemDtos = cartItemService.getCartItemsByCartId(cart.getId());

        if (cartItemDtos.isEmpty()) {
            throw new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("cart.empty"));
        }

        return new BasicMessageResponse<>(200, null, cartItemDtos);
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
    public BasicMessageResponse<?> clearCartItems(Integer cartId) {
        checkExistsById(cartId);
        cartItemService.clearCartItems(cartId);
        cartRepository.updateCartTotal(cartId, BigDecimal.ZERO);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("cart.item.success_clear_all"), null);
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
