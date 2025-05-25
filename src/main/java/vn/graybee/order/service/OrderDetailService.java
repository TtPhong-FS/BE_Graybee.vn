package vn.graybee.order.service;

import vn.graybee.cart.model.CartItem;
import vn.graybee.order.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetail> createOrderDetail(Long orderId, List<CartItem> cartItems);

}
