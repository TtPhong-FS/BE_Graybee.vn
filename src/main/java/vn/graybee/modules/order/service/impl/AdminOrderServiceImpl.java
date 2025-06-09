package vn.graybee.modules.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.common.dto.PaginationInfo;
import vn.graybee.common.dto.SortInfo;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.admin.ConfirmOrderResponse;
import vn.graybee.modules.order.enums.DeliveryType;
import vn.graybee.modules.order.enums.OrderStatus;
import vn.graybee.modules.order.enums.PaymentStatus;
import vn.graybee.modules.order.repository.OrderRepository;
import vn.graybee.modules.order.service.AdminOrderService;

import java.util.List;

@AllArgsConstructor
@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;

    private final MessageSourceUtil messageSourceUtil;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ConfirmOrderResponse> confirmOrderById(long orderId) {

        if (!orderRepository.checkExistsById(orderId)) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get(""));
        }

        orderRepository.confirmOrderById(orderId);

        ConfirmOrderResponse response = new ConfirmOrderResponse(orderId, true, OrderStatus.CONFIRMED);

        return new BasicMessageResponse<>(200, messageSourceUtil.get(""), response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CancelOrderResponse> cancelOrderById(long orderId) {

        if (!orderRepository.checkExistsById(orderId)) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get(""));
        }

        OrderStatus status = orderRepository.findStatusById(orderId);

        if (status.equals(OrderStatus.PENDING) || status.equals(OrderStatus.CONFIRMED) || status.equals(OrderStatus.PROCESSING)) {
            orderRepository.cancelOrderById(orderId);
        } else {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get(""));
        }

        CancelOrderResponse response = new CancelOrderResponse(orderId, true, OrderStatus.CANCELLED);

        return new BasicMessageResponse<>(200, messageSourceUtil.get(""), response);
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

        String message = orderPage.getContent().isEmpty() ? messageSourceUtil.get("") : null;

        return new MessageResponse<>(200, message, orderPage.getContent(), paginationInfo, sortInfo);
    }


}
