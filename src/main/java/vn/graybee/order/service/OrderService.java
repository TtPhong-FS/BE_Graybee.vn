package vn.graybee.order.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.order.dto.request.OrderCreateRequest;
import vn.graybee.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.order.dto.response.admin.ConfirmOrderResponse;
import vn.graybee.order.dto.response.user.OrderHistoryResponse;

import java.util.List;

public interface OrderService {

    MessageResponse<List<AdminOrderResponse>> findAll(int page, int size, String sortBy, String order);

    BasicMessageResponse<?> createOrder(OrderCreateRequest request, String userUid, String sessionId);

    BasicMessageResponse<ConfirmOrderResponse> confirmOrder(long orderId);

    BasicMessageResponse<CancelOrderResponse> cancelOrder(long orderId);

    BasicMessageResponse<List<OrderHistoryResponse>> findOrderHistoriesByUserUid(String userUid, String status);

}
