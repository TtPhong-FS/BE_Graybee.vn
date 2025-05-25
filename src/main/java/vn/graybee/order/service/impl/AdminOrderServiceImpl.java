package vn.graybee.order.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantOrder;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.common.dto.PaginationInfo;
import vn.graybee.common.dto.SortInfo;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.order.dto.response.admin.ConfirmOrderResponse;
import vn.graybee.order.enums.DeliveryType;
import vn.graybee.order.enums.OrderStatus;
import vn.graybee.order.enums.PaymentStatus;
import vn.graybee.order.repository.OrderRepository;
import vn.graybee.order.service.AdminOrderService;

import java.util.List;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;

    public AdminOrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ConfirmOrderResponse> confirmOrderById(long orderId) {

        if (!orderRepository.checkExistsById(orderId)) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantOrder.does_not_exists);
        }

        orderRepository.confirmOrderById(orderId);

        ConfirmOrderResponse response = new ConfirmOrderResponse(orderId, true, OrderStatus.CONFIRMED);

        return new BasicMessageResponse<>(200, ConstantOrder.success_confirm, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CancelOrderResponse> cancelOrderById(long orderId) {

        if (!orderRepository.checkExistsById(orderId)) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantOrder.does_not_exists);
        }

        OrderStatus status = orderRepository.findStatusById(orderId);

        if (status.equals(OrderStatus.PENDING) || status.equals(OrderStatus.CONFIRMED) || status.equals(OrderStatus.PROCESSING)) {
            orderRepository.cancelOrderById(orderId);
        } else {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantOrder.cannot_cancel_order_already_delivery);
        }

        CancelOrderResponse response = new CancelOrderResponse(orderId, true, OrderStatus.CANCELLED);

        return new BasicMessageResponse<>(200, ConstantOrder.success_cancel, response);
    }


    @Override
    public MessageResponse<List<AdminOrderResponse>> getOrderListForDashboard(int page, int size, String sortBy, String order) {

        Sort sort = order.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AdminOrderResponse> orderPage = orderRepository.fetchAll(pageable);

        PaginationInfo paginationInfo = new PaginationInfo(
                orderPage.getNumber(),
                orderPage.getTotalPages(),
                orderPage.getTotalElements(),
                orderPage.getSize()
        );

        SortInfo sortInfo = new SortInfo(sortBy, order);

        for (AdminOrderResponse re : orderPage.getContent()) {
            if (re.getPaymentStatus() == null) {
                re.setPaymentStatus(PaymentStatus.UNPAID);
            }
            if (re.getDeliveryType() == null) {
                re.setDeliveryType(DeliveryType.HOME_DELIVERY);
            }
        }

        String message = orderPage.getContent().isEmpty() ? ConstantOrder.order_empty : null;

        return new MessageResponse<>(200, message, orderPage.getContent(), paginationInfo, sortInfo);
    }


}
