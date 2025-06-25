package vn.graybee.modules.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.order.dto.response.DeliveryDto;
import vn.graybee.modules.order.dto.response.OrderDetailDto;
import vn.graybee.modules.order.dto.response.OrderItemDto;
import vn.graybee.modules.order.dto.response.PaymentDto;
import vn.graybee.modules.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.admin.ConfirmOrderResponse;
import vn.graybee.modules.order.dto.response.admin.OrderStatusResponse;
import vn.graybee.modules.order.enums.OrderStatus;
import vn.graybee.modules.order.repository.OrderRepository;
import vn.graybee.modules.order.service.AdminOrderService;
import vn.graybee.modules.order.service.DeliveryService;
import vn.graybee.modules.order.service.OrderDetailService;
import vn.graybee.modules.order.service.PaymentService;

import java.util.List;

@AllArgsConstructor
@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;

    private final MessageSourceUtil messageSourceUtil;

    private final OrderDetailService orderDetailService;

    private final DeliveryService deliveryService;

    private final PaymentService paymentService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ConfirmOrderResponse confirmOrderById(long orderId) {

        OrderStatus currentStatus = orderRepository.findStatusById(orderId);

        if (currentStatus == OrderStatus.CONFIRMED) {
            throw new BusinessCustomException(Constants.Common.global, "Đơn hàng đã được xác nhận, không thể xác nhận lại.");
        }

        if (currentStatus == OrderStatus.CANCELLED) {
            throw new BusinessCustomException(Constants.Common.global, "Đơn hàng đã bị huỷ, không thể xác nhận.");
        }

        orderRepository.updateStatusByIdAndStatus(orderId, OrderStatus.CONFIRMED);

        return new ConfirmOrderResponse(orderId, OrderStatus.CONFIRMED);
    }

    @Override
    public void checkExistsById(long id) {
        if (!orderRepository.checkExistsById(id)) {
            throw new BusinessCustomException(Constants.Common.global, "Đơn hàng không tồn tại");
        }

    }

    @Override
    public Long deleteOrderById(long id) {
        checkExistsById(id);

        orderRepository.deleteById(id);

        return id;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public OrderStatusResponse updateStatusOrderById(long id, String status) {
        checkExistsById(id);

        OrderStatus orderStatus = OrderStatus.fromString(status, messageSourceUtil);

        orderRepository.updateStatusByIdAndStatus(id, orderStatus);

        return new OrderStatusResponse(id, status);
    }

    @Override
    public OrderDetailDto getOrderDetailById(long id) {

        checkExistsById(id);

        OrderDetailDto orderDetailDto = orderRepository.findOrderDetailByOrderId(id);

        List<OrderItemDto> orderItemDtos = orderDetailService.findItemByOrderId(id);

        DeliveryDto deliveryDto = deliveryService.findDeliveryDtoByOrderId(id);
        PaymentDto paymentDto = paymentService.findPaymentDtoByOrderId(id);


        orderDetailDto.setOrderItems(orderItemDtos);
        orderDetailDto.setDelivery(deliveryDto);
        orderDetailDto.setPayment(paymentDto);

        return orderDetailDto;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CancelOrderResponse cancelOrderById(long orderId) {

        checkExistsById(orderId);

        OrderStatus currentStatus = orderRepository.findStatusById(orderId);

        if (currentStatus == OrderStatus.PENDING || currentStatus == OrderStatus.CONFIRMED || currentStatus == OrderStatus.PROCESSING) {
            orderRepository.updateStatusByIdAndStatus(orderId, OrderStatus.CANCELLED);
        } else {
            throw new BusinessCustomException(Constants.Common.global, "Không thể huỷ, đơn hàng đã được giao cho vận chuyển.");
        }

        orderDetailService.increaseQuantityByOrderId(orderId);

        return new CancelOrderResponse(orderId, OrderStatus.CANCELLED);
    }


    @Override
    public List<AdminOrderResponse> getOrderListForDashboard() {
        return orderRepository.fetchAll();

    }


}
