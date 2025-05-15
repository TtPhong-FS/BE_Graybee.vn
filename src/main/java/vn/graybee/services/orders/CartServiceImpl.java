package vn.graybee.services.orders;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantCart;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.orders.Cart;
import vn.graybee.models.orders.CartItem;
import vn.graybee.repositories.orders.CartItemRepository;
import vn.graybee.repositories.orders.CartRepository;
import vn.graybee.requests.orders.AddCartItemRequest;
import vn.graybee.requests.orders.RemoveCartItemRequest;
import vn.graybee.response.publics.carts.AddItemToCartResponse;
import vn.graybee.response.publics.carts.CartItemResponse;
import vn.graybee.response.publics.carts.DecreaseQuantityResponse;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductPriceResponse;
import vn.graybee.services.products.IProductServicePublic;
import vn.graybee.services.users.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final UserService userService;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final IProductServicePublic productServicePublic;

    public CartServiceImpl(UserService userService, CartRepository cartRepository, CartItemRepository cartItemRepository, IProductServicePublic productServicePublic) {
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productServicePublic = productServicePublic;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<AddItemToCartResponse> addItemToCart(AddCartItemRequest request, Integer userUid, String sessionId) {

        if (userUid != null && sessionId != null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.conflict_UserUid_and_sessionId);
        }

        Cart cart;

        if (userUid != null) {
            cart = cartRepository.findByUserUid(userUid).orElseGet(Cart::new);
        } else if (sessionId != null) {
            cart = cartRepository.findBySessionId(sessionId).orElseGet(Cart::new);
        } else {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.invalid_request);
        }

        boolean isNewCart = cart.getId() == null;

        if (isNewCart) {
            cart.setUserUid(userUid);
            cart.setSessionId(userUid == null ? sessionId : null);
            cart.setTotalAmount(BigDecimal.ZERO);
        }
        cart = cartRepository.save(cart);


        ProductBasicResponse product = productServicePublic.findBasicToAddToCartById(request.getProductId());

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        for (CartItem cartItem : items) {
            if (cartItem.getProductId().equals(product.getId())) {
                int newQuantity = cartItem.getQuantity() + request.getQuantity();

                CartItemResponse item = updateCartItemQuantity(cartItem.getId(), newQuantity, product.getFinalPrice());

                updateTotalAmount(cart.getId());

                AddItemToCartResponse response = new AddItemToCartResponse(cartItem.getId(), product, item.getQuantity(), item.getTotal());

                return new BasicMessageResponse<>(200, ConstantCart.success_update_quantity_to_cart, response);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setCartId(cart.getId());
        newCartItem.setProductId(product.getId());
        newCartItem.setQuantity(request.getQuantity());
        newCartItem.setTotal(product.getFinalPrice().multiply(BigDecimal.valueOf(request.getQuantity())));

        newCartItem = cartItemRepository.save(newCartItem);

        updateTotalAmount(cart.getId());

        AddItemToCartResponse response = new AddItemToCartResponse(newCartItem.getId(), product, newCartItem.getQuantity(), newCartItem.getTotal());

        return new BasicMessageResponse<>(201, ConstantCart.success_add_to_cart, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CartItemResponse updateCartItemQuantity(int cartItemId, int quantity, BigDecimal price) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_item_does_not_exists));

        cartItem.setQuantity(quantity);
        cartItem.setTotal(price.multiply(BigDecimal.valueOf(quantity)));
        cartItem = cartItemRepository.save(cartItem);

        return new CartItemResponse(cartItem.getQuantity(), cartItem.getTotal());
    }

    @Override
    public BasicMessageResponse<Cart> findById(int id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_does_not_exists));

        return new BasicMessageResponse<>(200, ConstantCart.success_find_cart, cart);
    }

    @Override
    public BasicMessageResponse<List<AddItemToCartResponse>> findCartByUserUidOrSessionId(Integer userUid, String sessionId) {

        if (userUid != null && sessionId != null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.conflict_UserUid_and_sessionId);
        }

        Integer cartId;

        if (userUid != null) {
            cartId = cartRepository.findIdByUserUid(userUid).orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_does_not_exists));
        } else if (sessionId != null) {
            cartId = cartRepository.findIdBySessionId(sessionId).orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_does_not_exists));
        } else {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.invalid_request);
        }

        List<CartItem> items = cartItemRepository.findByCartId(cartId);

        List<AddItemToCartResponse> response = new ArrayList<>();

        if (items.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantCart.item_empty, response);
        }

        for (CartItem cartItem : items) {
            ProductBasicResponse product = productServicePublic.findBasicToAddToCartById(cartItem.getProductId());
            response.add(new AddItemToCartResponse(cartItem.getId(), product, cartItem.getQuantity(), cartItem.getTotal()));
        }

        return new BasicMessageResponse<>(200, ConstantCart.success_find_cart, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<?> clearItemsToCart(Integer userUid, String sessionId) {

        if (userUid != null && sessionId != null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.conflict_UserUid_and_sessionId);
        }

        Integer cartId;

        if (userUid != null) {

            cartId = cartRepository.findIdByUserUid(userUid)
                    .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_does_not_exists));
        } else if (sessionId != null) {
            cartId = cartRepository.findIdBySessionId(sessionId)
                    .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_does_not_exists));
        } else {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.invalid_request);
        }

        cartItemRepository.deleteByCartId(cartId);
        cartRepository.updateTotalAmount(cartId, BigDecimal.ZERO);

        return new BasicMessageResponse<>(200, ConstantCart.success_clear_items, Collections.emptyList());
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> deleteCartItemToCart(int cartItemId, Integer userUid, String sessionId) {
        if (userUid != null && sessionId != null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.conflict_UserUid_and_sessionId);
        }

        Integer cartId;

        if (userUid != null) {

            cartId = cartRepository.findIdByUserUid(userUid)
                    .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_does_not_exists));
        } else if (sessionId != null) {
            cartId = cartRepository.findIdBySessionId(sessionId)
                    .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_does_not_exists));
        } else {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.invalid_request);
        }

        int id = cartItemRepository.checkExistsById(cartItemId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_item_does_not_exists));

        cartItemRepository.deleteById(id);

        updateTotalAmount(cartId);

        return new BasicMessageResponse<>(200, ConstantCart.success_delete_item_to_cart, id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<DecreaseQuantityResponse> decreaseQuantityToCartItem(RemoveCartItemRequest request, Integer userUid, String sessionId) {

        if (userUid != null && sessionId != null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.conflict_UserUid_and_sessionId);
        }

        Integer cartId;

        if (userUid != null) {
            cartId = cartRepository.findIdByUserUid(userUid)
                    .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_does_not_exists));
        } else if (sessionId != null) {
            cartId = cartRepository.findIdBySessionId(sessionId)
                    .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_does_not_exists));
        } else {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.invalid_request);
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cartId, request.getProductId())
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_item_does_not_exists));

        if (!cartItem.getProductId().equals(request.getProductId())) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantCart.product_not_exists_in_cart_item);
        }

        DecreaseQuantityResponse response = new DecreaseQuantityResponse();
        if (cartItem.getQuantity() <= request.getQuantity()) {
            cartItemRepository.deleteById(cartItem.getId());
            updateTotalAmount(cartItem.getCartId());

            response.setId(cartItem.getId());
            response.setTotal(BigDecimal.ZERO);
            response.setQuantity(0);

            return new BasicMessageResponse<>(200, ConstantCart.success_delete_item_to_cart, response);
        }

        ProductPriceResponse product = productServicePublic.getPriceById(request.getProductId());

        cartItem.setQuantity(cartItem.getQuantity() - request.getQuantity());

        cartItem.setTotal(cartItem.getTotal().subtract(product.getFinalPrice().multiply(BigDecimal.valueOf(request.getQuantity()))));

        cartItem = cartItemRepository.save(cartItem);

        updateTotalAmount(cartItem.getCartId());

        response.setTotal(cartItem.getTotal());
        response.setId(cartItem.getId());
        response.setQuantity(cartItem.getQuantity());

        return new BasicMessageResponse<>(200, ConstantCart.success_remove_item_to_cart, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateTotalAmount(int cartId) {
        BigDecimal totalAmount = cartItemRepository.sumTotalByCartId(cartId);
        cartRepository.updateTotalAmount(cartId, totalAmount);
    }

    @Override
    public Integer findByUserIdOrSessionId(Integer userUid, String sessionId) {
        if (userUid != null && sessionId != null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.conflict_UserUid_and_sessionId);
        }

        Integer cartId = null;
        if (userUid != null) {
            cartId = cartRepository.findIdByUserUid(userUid)
                    .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_does_not_exists));
        } else if (sessionId != null) {
            cartId = cartRepository.findIdBySessionId(sessionId)
                    .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_does_not_exists));
        }

        return cartId;
    }


}
