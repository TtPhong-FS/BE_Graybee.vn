package vn.graybee.modules.order.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.modules.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.admin.ConfirmOrderResponse;

import java.util.List;

public interface AdminOrderService {

    BasicMessageResponse<ConfirmOrderResponse> confirmOrderById(long orderId);

    BasicMessageResponse<CancelOrderResponse> cancelOrderById(long orderId);

    MessageResponse<List<AdminOrderResponse>> getOrderListForDashboard(int page, int size, String sortBy, String order);

}
