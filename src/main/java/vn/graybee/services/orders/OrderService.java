package vn.graybee.services.orders;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.requests.orders.OrderCreateRequest;
import vn.graybee.response.admin.orders.AdminOrderResponse;
import vn.graybee.response.admin.orders.CancelOrderResponse;
import vn.graybee.response.admin.orders.ConfirmOrderResponse;
import vn.graybee.response.orders.OrderHistoryResponse;

import java.util.List;

public interface OrderService {

    MessageResponse<List<AdminOrderResponse>> findAll(int page, int size, String sortBy, String order);

    BasicMessageResponse<?> createOrder(OrderCreateRequest request, Integer userUid, String sessionId);

    BasicMessageResponse<ConfirmOrderResponse> confirmOrder(long orderId);

    BasicMessageResponse<CancelOrderResponse> cancelOrder(long orderId);

    BasicMessageResponse<List<OrderHistoryResponse>> findOrderHistoriesByUserUid(Integer userUid, String status);

}
