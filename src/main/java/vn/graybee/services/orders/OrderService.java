package vn.graybee.services.orders;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.orders.OrderCreateRequest;
import vn.graybee.response.admin.orders.CancelOrderResponse;
import vn.graybee.response.admin.orders.ConfirmOrderResponse;
import vn.graybee.response.orders.OrderHistoryResponse;

import java.util.List;

public interface OrderService {

    BasicMessageResponse<List<OrderHistoryResponse>> findOrderHistoriesByUserUid(Integer userUid, String status);
    
    BasicMessageResponse<?> createOrder(OrderCreateRequest request, Integer userUid, String sessionId);

    BasicMessageResponse<ConfirmOrderResponse> confirmOrder(long orderId);

    BasicMessageResponse<CancelOrderResponse> cancelOrder(long orderId, Integer userUid);

}
