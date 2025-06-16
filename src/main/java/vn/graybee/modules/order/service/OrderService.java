package vn.graybee.modules.order.service;

import vn.graybee.modules.order.dto.request.OrderCreateRequest;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.user.OrderHistoryResponse;

import java.util.List;

public interface OrderService {

    List<Long> createOrder(OrderCreateRequest request, Long accountId, String sessionId);

    CancelOrderResponse cancelOrderById(long orderId);

    List<OrderHistoryResponse> getOrderHistoryByAccountId(Long accountId);

    void transformOrdersToAccountBySessionId(Long accountId, String sessionId);

    CancelOrderResponse cancelOrderByCode(String code);

}
