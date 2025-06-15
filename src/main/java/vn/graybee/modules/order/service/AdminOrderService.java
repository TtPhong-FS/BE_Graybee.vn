package vn.graybee.modules.order.service;

import vn.graybee.modules.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.admin.ConfirmOrderResponse;

import java.util.List;

public interface AdminOrderService {

    ConfirmOrderResponse confirmOrderById(long orderId);

    CancelOrderResponse cancelOrderById(long orderId);

    List<AdminOrderResponse> getOrderListForDashboard(int page, int size, String sortBy, String order);

    void checkExistsById(long id);

}
