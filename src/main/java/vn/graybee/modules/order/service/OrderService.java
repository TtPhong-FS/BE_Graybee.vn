package vn.graybee.modules.order.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.order.dto.request.OrderCreateRequest;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.user.OrderHistoryResponse;

import java.util.List;

public interface OrderService {

    BasicMessageResponse<?> createOrder(OrderCreateRequest request, Long accountId, String sessionId);

    CancelOrderResponse cancelOrderById(long orderId);

    BasicMessageResponse<List<OrderHistoryResponse>> getOrderHistoryByAccountId(Long accountId, String status);

    void transformOrdersToAccountBySessionId(Long accountId, String sessionId);

}
