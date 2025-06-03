package vn.graybee.modules.order.service;

import vn.graybee.modules.cart.model.CartItem;
import vn.graybee.modules.order.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetail> createOrderDetail(Long orderId, List<CartItem> cartItems);

}
