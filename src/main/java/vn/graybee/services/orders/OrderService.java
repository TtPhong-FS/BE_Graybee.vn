package vn.graybee.services.orders;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.orders.OrderCreateRequest;
import vn.graybee.response.orders.OrderHistoryResponse;

import java.util.List;

public interface OrderService {

    BasicMessageResponse<List<OrderHistoryResponse>> findOrderHistoriesByUserUid(int userUid);

    BasicMessageResponse<List<OrderHistoryResponse>> findOrderHistoryByUserUidAndStatus(int userUid, String status);

    BasicMessageResponse<?> createOrder(OrderCreateRequest request, int userUid);

}
