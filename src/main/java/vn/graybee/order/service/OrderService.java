package vn.graybee.order.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.order.dto.request.OrderCreateRequest;
import vn.graybee.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.order.dto.response.user.OrderHistoryResponse;

import java.util.List;

public interface OrderService {

    BasicMessageResponse<?> createOrder(OrderCreateRequest request, Long accountId, String sessionId);

    BasicMessageResponse<CancelOrderResponse> cancelOrderById(long orderId);

    BasicMessageResponse<List<OrderHistoryResponse>> getOrderHistoryByAccountId(Long accountId, String status);

}
