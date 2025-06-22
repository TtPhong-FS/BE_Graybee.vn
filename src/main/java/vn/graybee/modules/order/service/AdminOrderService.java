package vn.graybee.modules.order.service;

import vn.graybee.modules.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.admin.ConfirmOrderResponse;
import vn.graybee.modules.order.dto.response.admin.OrderStatusResponse;

import java.util.List;

public interface AdminOrderService {

    ConfirmOrderResponse confirmOrderById(long orderId);

    CancelOrderResponse cancelOrderById(long orderId);

    List<AdminOrderResponse> getOrderListForDashboard();

    void checkExistsById(long id);

    Long deleteOrderById(long id);

    OrderStatusResponse updateStatusOrderById(long id, String status);

}
