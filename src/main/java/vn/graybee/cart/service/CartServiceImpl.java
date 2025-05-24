package vn.graybee.cart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.cart.dto.request.AddCartItemRequest;
import vn.graybee.cart.dto.request.RemoveCartItemRequest;
import vn.graybee.cart.dto.response.CartItemBasicDto;
import vn.graybee.cart.dto.response.CartItemDto;
import vn.graybee.cart.entity.Cart;
import vn.graybee.cart.entity.CartItem;
import vn.graybee.cart.repository.CartItemRepository;
import vn.graybee.cart.repository.CartRepository;
import vn.graybee.common.constants.ConstantCart;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.customer.service.CustomerService;
import vn.graybee.product.service.ProductService;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductPriceResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final UserService userService;

    private final CustomerService customerService;

    private final MessageSourceUtil messageSourceUtil;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductService productService;

    public CartServiceImpl(UserService userService, CustomerService customerService, MessageSourceUtil messageSourceUtil, CartRepository cartRepository, CartItemRepository cartItemRepository, ProductService productService) {
        this.userService = userService;
        this.customerService = customerService;
        this.messageSourceUtil = messageSourceUtil;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CartItemDto> addItemToCart(AddCartItemRequest request, String userUid, String sessionId) {

        if (userUid == null && sessionId == null) {
            throw new BusinessCustomException(ConstantGeneral.general, messageSourceUtil.get(""));
        }

        Cart cart = null;

        if (userUid != null) {
            cart = cartRepository.findByUserUid(userUid).orElseGet(Cart::new);
        }

        if (sessionId != null && userUid == null) {
            cart = cartRepository.findBySessionId(sessionId).orElseGet(Cart::new);
        }

        boolean isNewCart = cart == null;

        if (isNewCart) {
            cart.setSessionId(sessionId);
            cart.setTotalAmount(BigDecimal.ZERO);
        }
        cart = cartRepository.save(cart);


        ProductBasicResponse product = productService.findBasicToAddToCartById(request.getProductId());

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        for (CartItem cartItem : items) {
            if (cartItem.getProductId().equals(product.getId())) {
                int newQuantity = cartItem.getQuantity() + request.getQuantity();

                CartItemBasicDto item = updateCartItemQuantity(cartItem.getId(), newQuantity, product.getFinalPrice());

                updateTotalAmount(cart.getId());

                CartItemDto response = new CartItemDto(cartItem.getId(), product, item.getQuantity(), item.getTotal());

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

        CartItemDto response = new CartItemDto(newCartItem.getId(), product, newCartItem.getQuantity(), newCartItem.getTotal());

        return new BasicMessageResponse<>(201, ConstantCart.success_add_to_cart, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CartItemBasicDto updateCartItemQuantity(int cartItemId, int quantity, BigDecimal price) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_item_does_not_exists));

        cartItem.setQuantity(quantity);
        cartItem.setTotal(price.multiply(BigDecimal.valueOf(quantity)));
        cartItem = cartItemRepository.save(cartItem);

        return new CartItemBasicDto(cartItem.getId(), cartItem.getQuantity(), cartItem.getTotal());
    }

    @Override
    public BasicMessageResponse<Cart> findById(int id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantCart.cart_does_not_exists));

        return new BasicMessageResponse<>(200, ConstantCart.success_find_cart, cart);
    }

    @Override
    public BasicMessageResponse<List<CartItemDto>> findCartByUserUidOrSessionId(String userUid, String sessionId) {

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

        List<CartItemDto> response = new ArrayList<>();

        if (items.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantCart.item_empty, response);
        }

        for (CartItem cartItem : items) {
            ProductBasicResponse product = productService.findBasicToAddToCartById(cartItem.getProductId());
            response.add(new CartItemDto(cartItem.getId(), product, cartItem.getQuantity(), cartItem.getTotal()));
        }

        return new BasicMessageResponse<>(200, ConstantCart.success_find_cart, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<?> clearItemsToCart(String userUid, String sessionId) {

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
    public BasicMessageResponse<Integer> deleteCartItemToCart(int cartItemId, String userUid, String sessionId) {
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
    public BasicMessageResponse<CartItemBasicDto> decreaseQuantityToCartItem(RemoveCartItemRequest request, String userUid, String sessionId) {

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

        CartItemBasicDto response = new CartItemBasicDto();
        if (cartItem.getQuantity() <= request.getQuantity()) {
            cartItemRepository.deleteById(cartItem.getId());
            updateTotalAmount(cartItem.getCartId());

            response.setId(cartItem.getId());
            response.setTotal(BigDecimal.ZERO);
            response.setQuantity(0);

            return new BasicMessageResponse<>(200, ConstantCart.success_delete_item_to_cart, response);
        }

        ProductPriceResponse product = productService.getPriceById(request.getProductId());

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
    public Integer findByUserIdOrSessionId(String userUid, String sessionId) {
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
