package vn.graybee.modules.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
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
    @Transactional(rollbackFor = RuntimeException.class)
    public CancelOrderResponse cancelOrderById(long orderId) {

        checkExistsById(orderId);

        OrderStatus currentStatus = orderRepository.findStatusById(orderId);

        if (currentStatus == OrderStatus.PENDING || currentStatus == OrderStatus.CONFIRMED || currentStatus == OrderStatus.PROCESSING) {
            orderRepository.updateStatusByIdAndStatus(orderId, OrderStatus.CANCELLED);
        } else {
            throw new BusinessCustomException(Constants.Common.global, "Không thể huỷ, đơn hàng đã được giao cho vận chuyển.");
        }

        return new CancelOrderResponse(orderId, OrderStatus.CANCELLED);
    }


    @Override
    public List<AdminOrderResponse> getOrderListForDashboard(int page, int size, String sortBy, String order) {

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

        return orderPage.getContent();
    }


}
