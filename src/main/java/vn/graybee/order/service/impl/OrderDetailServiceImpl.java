package vn.graybee.order.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.cart.model.CartItem;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.inventory.service.InventoryService;
import vn.graybee.order.model.OrderDetail;
import vn.graybee.order.repository.OrderDetailRepository;
import vn.graybee.order.service.OrderDetailService;
import vn.graybee.product.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    private final MessageSourceUtil messageSourceUtil;

    private final ProductService productService;

    private final InventoryService inventoryService;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, MessageSourceUtil messageSourceUtil, ProductService productService, InventoryService inventoryService) {
        this.orderDetailRepository = orderDetailRepository;
        this.messageSourceUtil = messageSourceUtil;
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public List<OrderDetail> createOrderDetail(Long orderId, List<CartItem> cartItems) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItem cartItem : cartItems) {

            inventoryService.validateStockAvailable(cartItem.getProductId(), cartItem.getQuantity());

            BigDecimal priceAtTime = productService.getProductPriceById(cartItem.getProductId());
            BigDecimal subtotal = calculateSubtotal(priceAtTime, cartItem.getQuantity());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(cartItem.getProductId());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPriceAtTime(priceAtTime);
            orderDetail.setSubtotal(subtotal);

            orderDetails.add(orderDetail);

            inventoryService.decreaseStock(orderDetail.getProductId(), orderDetail.getQuantity());
        }

        return orderDetailRepository.saveAll(orderDetails);
    }

    private BigDecimal calculateSubtotal(BigDecimal price, int quantity) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

}
