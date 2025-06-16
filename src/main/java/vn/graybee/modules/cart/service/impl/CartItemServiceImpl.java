package vn.graybee.modules.cart.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.cart.dto.response.CartItemDto;
import vn.graybee.modules.cart.model.CartItem;
import vn.graybee.modules.cart.repository.CartItemRepository;
import vn.graybee.modules.cart.service.CartItemService;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;
import vn.graybee.modules.product.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final MessageSourceUtil messageSourceUtil;

    private final ProductService productService;

    private CartItemDto convertToCartItemDto(CartItem cartItem, ProductBasicResponse product) {
        return new CartItemDto(
                cartItem.getId(),
                product,
                cartItem.getQuantity(),
                cartItem.getTotalAmount()
        );
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CartItemDto addItemToCart(Long cartId, long productId) {

        ProductBasicResponse product = productService.getProductBasicInfoForCart(productId);

        Optional<CartItem> existingCartItem = cartItemRepository.findByProductIdAndCartId(productId, cartId);

        CartItem cartItem;

        if (existingCartItem.isPresent()) {
            cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() + 1;
            cartItem.setQuantity(newQuantity);
            cartItem.setTotalAmount(cartItem.getTotalAmount().add(cartItem.calculateTotal(product.getFinalPrice(), 1)));
        } else {
            cartItem = new CartItem();
            cartItem.setCartId(cartId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(1);
            cartItem.setTotalAmount(cartItem.calculateTotal(product.getFinalPrice(), 1));
        }

        cartItem = cartItemRepository.save(cartItem);

        return convertToCartItemDto(cartItem, product);

    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public CartItemDto decreaseItemQuantity(Long cartId, long productId) {
        CartItem cartItem = cartItemRepository.findByProductIdAndCartId(productId, cartId)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("cart.item.not.found")));

        ProductBasicResponse product = productService.getProductBasicInfoForCart(productId);

        int remainingQuantity = cartItem.getQuantity() - 1;

        if (remainingQuantity == 0) {
            cartItemRepository.deleteById(cartItem.getId());
            return null;
        } else {
            cartItem.setQuantity(remainingQuantity);
            cartItem.setTotalAmount(cartItem.getTotalAmount().add(cartItem.calculateTotal(product.getFinalPrice(), 1)));
            cartItem = cartItemRepository.save(cartItem);
        }

        return convertToCartItemDto(cartItem, product);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Long removeItemFromCart(Long cartId, Long cartItemId) {

        if (!cartItemRepository.existsById(cartItemId)) {
            throw new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("cart.item.not.found"));
        }

        cartItemRepository.deleteById(cartItemId);

        return cartItemId;
    }

    @Override
    public List<CartItemDto> getCartItemsByCartId(Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartId);

        if (cartItems.isEmpty()) {
            return new ArrayList<>();
        }

        List<CartItemDto> cartItemDtos = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            ProductBasicResponse product = productService.getProductBasicInfoForCart(cartItem.getProductId());
            cartItemDtos.add(new CartItemDto(
                    cartItem.getId(),
                    product,
                    cartItem.getQuantity(),
                    cartItem.getTotalAmount()
            ));
        }

        return cartItemDtos;
    }

    @Override
    public List<CartItem> getCartItemByIdsAndCartId(List<Long> cartItemIds, Long cartId) {
        return cartItemRepository.findByIdsAndCartId(cartItemIds, cartId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void clearCartItems(Long cartId) {
        cartItemRepository.deleteAllByCartId(cartId);
    }

    @Override
    public BigDecimal getCartItemTotals(Long cartId) {
        List<BigDecimal> itemTotals = cartItemRepository.findAllTotalByCartId(cartId);
        return itemTotals
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Long> clearCartItemsByIds(List<Long> cartItemIds, Long cartId) {
        List<CartItem> cartItems = cartItemRepository.findByIdsAndCartId(cartItemIds, cartId);
        cartItemRepository.deleteAll(cartItems);

        return cartItems.stream()
                .map(CartItem::getId)
                .toList();
    }

}
