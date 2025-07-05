package vn.graybee.modules.cart.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
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

import java.time.LocalDateTime;
import java.util.Collections;
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
    @Transactional(rollbackFor = RuntimeException.class)
    public CartItemDto findOrCreateCartAfterAddItem(Long accountId, String sessionId, long productId) {
        if (accountId == null && sessionId == null) {
            throw new BusinessCustomException(Constants.Common.global, "Missing session ID");
        }

        Cart cart;

        if (accountId != null) {
            cart = cartRepository.findByAccountIdAndSessionId(accountId, null)
                    .orElseGet(Cart::new);
        } else {
            cart = cartRepository.findByAccountIdAndSessionId(null, sessionId)
                    .orElseGet(Cart::new);
        }

        boolean isNewCart = cart.getId() == null;

        if (isNewCart) {
            if (accountId != null) {
                cart.setAccountId(accountId);
                cart.setSessionId(null);
            } else {
                cart.setSessionId(sessionId);
                cart.setAccountId(null);
            }
            cart.setTotalAmount(0);

            cartRepository.save(cart);
        }

        CartItemDto cartItemDto = cartItemService.addItemToCart(cart.getId(), productId);

        cartRepository.updateCartTotal(cart.getId(), cart.getTotalAmount() + (cartItemDto.getTotalAmount()));

        return cartItemDto;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void syncGuestCartToAccount(Long accountId, String sessionId) {
        if (accountId == null && sessionId == null) {
            throw new BusinessCustomException(Constants.Common.global, "Missing session ID");
        }

        Cart cart = cartRepository.findBySessionId(sessionId)
                .orElseGet(Cart::new);

        cart.setAccountId(accountId);
        cart.setSessionId(null);
        cart.setTotalAmount(0);

        cart = cartRepository.save(cart);

        updateCartTotal(cart.getId());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<CartItemDto> findCartByAccountIdOrSessionId(Long accountId, String sessionId) {

        Cart cart;

        if (accountId != null) {
            cart = cartRepository.findByAccountIdAndSessionId(accountId, null)
                    .orElseGet(Cart::new);
        } else if (sessionId != null) {
            cart = cartRepository.findByAccountIdAndSessionId(null, sessionId)
                    .orElseGet(Cart::new);
        } else {
            return Collections.emptyList();
        }

        boolean isNewCart = cart.getId() == null;

        if (isNewCart) {
            if (accountId != null) {
                cart.setAccountId(accountId);
                cart.setSessionId(null);
            } else {
                cart.setSessionId(sessionId);
                cart.setAccountId(null);
            }
            cart.setTotalAmount(0);

            cartRepository.save(cart);

            return Collections.emptyList();
        }

        return cartItemService.getCartItemsByCartId(cart.getId());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateCartTotal(Long cartId) {
        double totalAmount = cartItemService.getCartItemTotals(cartId);
        cartRepository.updateCartTotal(cartId, totalAmount);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void clearCartItems(Long cartId) {
        checkExistsById(cartId);
        cartItemService.clearCartItems(cartId);
        cartRepository.updateCartTotal(cartId, 0);

    }

    @Override
    public Long getCartIdByAccountIdOrSessionId(Long accountId, String sessionId) {
        if (accountId != null) {
            return cartRepository.findIdByAccountIdOrSessionId(accountId, null)
                    .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("cart.not.found")));
        } else {
            return cartRepository.findIdByAccountIdOrSessionId(null, sessionId)
                    .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("cart.not.found")));
        }
    }

    @Scheduled(cron = "0 0 0 ? * SUN")
    @Override
    public void removeCartNotUse() {
        List<Cart> carts = cartRepository.findAll();

        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        List<Cart> forRemove = carts
                .stream()
                .filter(c -> c.getAccountId() == null && c.getCreatedAt() != null && c.getCreatedAt().isBefore(sevenDaysAgo))
                .toList();

        cartRepository.deleteAllInBatch(forRemove);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void removeCartGarbage() {
        List<Cart> carts = cartRepository.findAll();

        List<Cart> forRemove = carts
                .stream()
                .filter(c -> c.getAccountId() == null && c.getSessionId() == null)
                .toList();

        cartRepository.deleteAllInBatch(forRemove);
    }

    private void checkExistsById(Long cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("cart.not.found"));
        }
    }

}
