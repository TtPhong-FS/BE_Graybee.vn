package vn.graybee.modules.order.service;

import vn.graybee.modules.cart.model.CartItem;
import vn.graybee.modules.order.dto.response.OrderItemDto;
import vn.graybee.modules.order.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetail> createOrderDetail(Long orderId, List<CartItem> cartItems);

    List<OrderDetail> findByOrderId(long orderId);

    void increaseQuantityByOrderId(long orderId);

    List<OrderItemDto> findItemByOrderId(long orderId);

}
