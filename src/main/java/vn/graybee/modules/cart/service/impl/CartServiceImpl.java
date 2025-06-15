package vn.graybee.modules.cart.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
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
    public CartItemDto findOrCreateCartAfterAddItem(Long accountId, String sessionId, long productId) {
        if (accountId == null && sessionId == null) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("common.bad_request"));
        }

        Cart cart = cartRepository.findByAccountIdAndSessionId(accountId, sessionId)
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

        return cartItemService.addItemToCart(cart.getId(), productId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void syncGuestCartToAccount(Long accountId, String sessionId) {
        if (accountId == null && sessionId == null) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("common.bad_request"));
        }

        Cart cart = cartRepository.findBySessionId(sessionId)
                .orElseGet(Cart::new);

        cart.setAccountId(accountId);
        cart.setSessionId(null);
        cart.setTotalAmount(BigDecimal.ZERO);

        cartRepository.save(cart);
    }

    @Override
    public List<CartItemDto> findCartByAccountIdOrSessionId(Long accountId, String sessionId) {
        Cart cart = cartRepository.findByAccountIdAndSessionId(accountId, sessionId)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("cart.not.found")));

        return cartItemService.getCartItemsByCartId(cart.getId());
    }

    @Override
    public void updateCartTotal(Integer cartId) {
        BigDecimal totalAmount = cartItemService.getCartItemTotals(cartId);
        cartRepository.updateCartTotal(cartId, totalAmount);
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
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("cart.not.found")));
    }

    private void checkExistsById(Integer cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("cart.not.found"));
        }
    }

}
