package vn.graybee.cart.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.cart.dto.request.AddCartItemRequest;
import vn.graybee.cart.dto.response.CartItemDto;
import vn.graybee.cart.model.CartItem;
import vn.graybee.cart.repository.CartItemRepository;
import vn.graybee.cart.service.CartItemService;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.inventory.service.InventoryService;
import vn.graybee.product.service.ProductService;
import vn.graybee.response.publics.products.ProductBasicResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final MessageSourceUtil messageSourceUtil;

    private final ProductService productService;

    private final InventoryService inventoryService;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, MessageSourceUtil messageSourceUtil, ProductService productService, InventoryService inventoryService) {
        this.cartItemRepository = cartItemRepository;
        this.messageSourceUtil = messageSourceUtil;
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    private BigDecimal calculateTotalPrice(BigDecimal price, int quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    private CartItemDto convertToCartItemDto(CartItem cartItem, ProductBasicResponse product) {
        return new CartItemDto(
                cartItem.getCartId(),
                cartItem.getQuantity(),
                cartItem.getTotal(),
                product
        );
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public BasicMessageResponse<CartItemDto> addItemToCart(Integer cartId, AddCartItemRequest request) {

        ProductBasicResponse product = productService.getProductBasicInfoForCart(request.getProductId());

        inventoryService.validateStockAvailable(request.getProductId(), request.getQuantity());

        Optional<CartItem> existingCartItem = cartItemRepository.findByProductIdAndCartId(request.getProductId(), cartId);

        CartItem cartItem;

        if (existingCartItem.isPresent()) {

            cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() + request.getQuantity();
            cartItem.setQuantity(newQuantity);
            cartItem.setTotal(cartItem.getTotal().add(calculateTotalPrice(product.getPrice(), request.getQuantity())));
        } else {

            cartItem = new CartItem();
            cartItem.setCartId(cartId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setTotal(calculateTotalPrice(product.getPrice(), request.getQuantity()));
        }

        cartItem = cartItemRepository.save(cartItem);

        CartItemDto cartItemDto = convertToCartItemDto(cartItem, product);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("cart.item.success_add"), cartItemDto);

    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public CartItemDto decreaseItemQuantity(Integer cartId, Long productId, int decreaseQuantity) {
        CartItem cartItem = cartItemRepository.findByProductIdAndCartId(productId, cartId)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("cart.item.not_found")));

        ProductBasicResponse product = productService.getProductBasicInfoForCart(productId);

        int remainingQuantity = cartItem.getQuantity() - decreaseQuantity;
        if (remainingQuantity == 0) {
            cartItemRepository.delete(cartItem);
            return null;
        } else {

            inventoryService.validateStockAvailable(productId, decreaseQuantity);

            cartItem.setQuantity(remainingQuantity);
            cartItem.setTotal(cartItem.getTotal().add(calculateTotalPrice(product.getPrice(), decreaseQuantity)));

            cartItem = cartItemRepository.save(cartItem);
        }

        return convertToCartItemDto(cartItem, product);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Integer removeItemFromCart(Integer cartId, Integer cartItemId) {

        Integer existingCartItemId = cartItemRepository.findByIdAndCartId(cartId, cartItemId)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("cart.item.not_found")));

        cartItemRepository.deleteById(existingCartItemId);

        return existingCartItemId;
    }

    @Override
    public List<CartItemDto> getCartItems(Integer cartId) {
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartId);

        List<CartItemDto> cartItemDtos = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            ProductBasicResponse product = productService.getProductBasicInfoForCart(cartItem.getProductId());
            cartItemDtos.add(new CartItemDto(
                    cartItem.getCartId(),
                    cartItem.getQuantity(),
                    cartItem.getTotal(),
                    product
            ));
        }

        return cartItemDtos;
    }

    @Override
    public List<CartItem> getCartItemByIdsAndCartId(List<Integer> cartItemIds, Integer cartId) {
        return cartItemRepository.findByIdsAndCartId(cartItemIds, cartId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void clearCartItems(Integer cartId) {
        cartItemRepository.deleteAllByCartId(cartId);
    }

    @Override
    public BigDecimal getCartItemTotals(Integer cartId) {
        List<BigDecimal> itemTotals = cartItemRepository.findAllTotalByCartId(cartId);
        return itemTotals
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Integer> clearCartItemsByIds(List<Integer> cartItemIds, Integer cartId) {
        List<CartItem> cartItems = cartItemRepository.findByIdsAndCartId(cartItemIds, cartId);
        cartItemRepository.deleteAll(cartItems);

        return cartItems.stream()
                .map(CartItem::getId)
                .toList();
    }

}
