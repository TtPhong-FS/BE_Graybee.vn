package vn.graybee.modules.order.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.cart.model.CartItem;
import vn.graybee.modules.inventory.service.InventoryService;
import vn.graybee.modules.order.dto.response.OrderItemDto;
import vn.graybee.modules.order.model.OrderDetail;
import vn.graybee.modules.order.repository.OrderDetailRepository;
import vn.graybee.modules.order.service.OrderDetailService;
import vn.graybee.modules.product.service.ProductService;

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

            inventoryService.validateQuantityAvailable(cartItem.getProductId(), cartItem.getQuantity());

            double priceAtTime = productService.getProductPriceById(cartItem.getProductId());
            double subtotal = calculateSubtotal(priceAtTime, cartItem.getQuantity());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(cartItem.getProductId());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPriceAtTime(priceAtTime);
            orderDetail.setSubtotal(subtotal);

            orderDetails.add(orderDetail);

            inventoryService.decreaseQuantity(orderDetail.getProductId(), orderDetail.getQuantity());
        }

        return orderDetailRepository.saveAll(orderDetails);
    }

    @Override
    public List<OrderDetail> findByOrderId(long orderId) {
        return orderDetailRepository.findAllByOrderId(orderId);
    }

    @Override
    public void increaseQuantityByOrderId(long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(orderId);


        for (OrderDetail orderDetail : orderDetails) {
            inventoryService.increaseQuantity(orderDetail.getProductId(), orderDetail.getQuantity());
        }

    }

    @Override
    public List<OrderItemDto> findItemByOrderId(long orderId) {
        return orderDetailRepository.findItemByOrderId(orderId);
    }


    private double calculateSubtotal(double price, int quantity) {
        return price * quantity;
    }

}
