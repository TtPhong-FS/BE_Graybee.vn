package vn.graybee.modules.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.CodeGenerator;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.model.Address;
import vn.graybee.modules.account.service.AddressService;
import vn.graybee.modules.account.service.CustomerService;
import vn.graybee.modules.cart.model.CartItem;
import vn.graybee.modules.cart.service.CartItemService;
import vn.graybee.modules.cart.service.CartService;
import vn.graybee.modules.dashboard.dto.OrderTotalResponse;
import vn.graybee.modules.order.dto.request.OrderCreateRequest;
import vn.graybee.modules.order.dto.response.DeliveryDto;
import vn.graybee.modules.order.dto.response.OrderDetailDto;
import vn.graybee.modules.order.dto.response.OrderItemDto;
import vn.graybee.modules.order.dto.response.PaymentDto;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.user.OrderHistoryResponse;
import vn.graybee.modules.order.enums.OrderStatus;
import vn.graybee.modules.order.model.Order;
import vn.graybee.modules.order.model.OrderDetail;
import vn.graybee.modules.order.repository.OrderRepository;
import vn.graybee.modules.order.service.DeliveryService;
import vn.graybee.modules.order.service.OrderDetailService;
import vn.graybee.modules.order.service.OrderService;
import vn.graybee.modules.order.service.PaymentService;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final MessageSourceUtil messageSourceUtil;

    private final OrderRepository orderRepository;

    private final DeliveryService deliveryService;

    private final OrderDetailService orderDetailService;

    private final PaymentService paymentService;

    private final AddressService addressService;

    private final CartItemService cartItemService;

    private final CartService cartService;

    private final CustomerService customerService;


    @Override
    public List<OrderHistoryResponse> getOrderHistoryByAccountId(Long accountId) {
        return orderRepository.findAllOrderHistoryResponseByAccountId(accountId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void transformOrdersToAccountBySessionId(Long accountId, String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) return;

        List<Long> orderIds = orderRepository.findIdsBySessionId(sessionId);

        if (!orderIds.isEmpty()) {
            orderRepository.transformOrdersToAccountByIds(accountId, orderIds);

            OrderTotalResponse orderTotalResponse = orderRepository.countByAccountId(accountId);

            customerService.updateTotalOrdersByAccountId(accountId, orderTotalResponse.getCount());
            customerService.updateTotalSpentByAccountId(accountId, orderTotalResponse.getTotalAmount());
        }

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CancelOrderResponse cancelOrderByCode(String code) {
        long id = orderRepository.findIdByCode(code)
                .orElseThrow(() -> new BusinessCustomException(Constants.Common.global, "Không tìm thấy đơn hàng trong hệ thống"));

        OrderStatus orderStatus = orderRepository.findStatusByCode(code);

        if (orderStatus == OrderStatus.COMPLETED) {
            throw new BusinessCustomException(Constants.Common.global, "Đơn hàng đã hoàn thành, không thể huỷ.");
        }

        if (orderStatus == OrderStatus.RETURNED) {
            throw new BusinessCustomException(Constants.Common.global, "Đơn hàng đang được trả lại, không thể huỷ.");
        }

        if (orderStatus == OrderStatus.CANCELLED) {
            throw new BusinessCustomException(Constants.Common.global, "Đơn hàng đã được huỷ, không thể huỷ lần nữa.");
        }

        if (orderStatus == OrderStatus.PROCESSING) {
            throw new BusinessCustomException(Constants.Common.global, "Đơn hàng đang được chuẩn bị để giao đến bạn, không thể huỷ.");
        }

        cancelOrderById(id);

        orderDetailService.increaseQuantityByOrderId(id);

        return new CancelOrderResponse(id, OrderStatus.CANCELLED);
    }

    @Override
    public OrderDetailDto getOrderDetailByCodeAndAccountId(String code, long accountId) {


        long orderId = orderRepository.findIdByCode(code)
                .orElseThrow(() -> new BusinessCustomException(Constants.Common.global, "Không tìm thấy đơn hàng trong hệ thống"));

        OrderDetailDto orderDetailDto = orderRepository.findOrderDetailByCodeAndAccountId(code, accountId);

        List<OrderItemDto> orderItemDtos = orderDetailService.findItemByOrderId(orderId);

        DeliveryDto deliveryDto = deliveryService.findDeliveryDtoByOrderId(orderId);
        PaymentDto paymentDto = paymentService.findPaymentDtoByOrderId(orderId);


        orderDetailDto.setOrderItems(orderItemDtos);
        orderDetailDto.setDelivery(deliveryDto);
        orderDetailDto.setPayment(paymentDto);

        return orderDetailDto;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<Long> createOrder(OrderCreateRequest request, Long accountId, String sessionId) {

        if (accountId == null && sessionId == null) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("common.request.invalid")
            );
        }

        Long cartId = cartService.getCartIdByAccountIdOrSessionId(accountId, sessionId);

        List<CartItem> cartItems = cartItemService.getCartItemByIdsAndCartId(request.getCartItemIds(), cartId);

        if (cartItems.isEmpty()) {
            throw new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("cart.item.empty"));
        }

        String code = CodeGenerator.generateCode(12, CodeGenerator.ALPHANUMERIC.toUpperCase());

        Order order = new Order();
        order.setNote(request.getCustomerInfo().getNote());
        order.setStatus(OrderStatus.PENDING);
        order.setCode(code);
        order.setEmail(request.getCustomerInfo().getEmail());

        if (accountId == null) {
            order.setSessionId(sessionId);
            order.setAccountId(null);
        } else {
            order.setAccountId(accountId);
            order.setSessionId(null);
        }
        order.setTotalAmount(0);
        order = orderRepository.save(order);

        List<OrderDetail> orderDetails = orderDetailService.createOrderDetail(order.getId(), cartItems);

        double totalAmount = orderDetails.stream()
                .mapToDouble(OrderDetail::getSubtotal)
                .sum();

        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);

        paymentService.savePaymentByOrderId(order.getId(), order.getTotalAmount(), request.getPaymentMethod());

        if (request.getAddressId() != null && accountId != null) {
            Long customerId = customerService.getIdByAccountId(accountId);
            customerService.updateTotalSpentByAccountId(customerId, order.getTotalAmount());
            customerService.updateTotalOrdersByAccountId(customerId, 1);
            Address address = addressService.getAddressByIdAndCustomerId(customerId, request.getAddressId(), request.getCustomerInfo(), request.getShippingInfo());
            deliveryService.createDeliveryForCustomer(order.getId(), request.getShippingInfo(), address);
        } else {
            deliveryService.createDeliveryForGuest(order.getId(), request.getCustomerInfo(), request.getShippingInfo());
        }

        cartItemService.clearCartItemsByIds(request.getCartItemIds(), cartId);
        cartService.updateCartTotal(cartId);

        return request.getCartItemIds();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CancelOrderResponse cancelOrderById(long orderId) {

        if (!orderRepository.checkExistsById(orderId)) {
            throw new BusinessCustomException(Constants.Common.global, "Đơn hàng không tồn tại");
        }

        OrderStatus currentStatus = orderRepository.findStatusById(orderId);

        if (currentStatus == OrderStatus.PENDING || currentStatus == OrderStatus.CONFIRMED) {
            orderRepository.updateStatusByIdAndStatus(orderId, OrderStatus.CANCELLED);
        } else {
            throw new BusinessCustomException(Constants.Common.global, "Không thể huỷ, đơn hàng đã được giao cho vận chuyển.");
        }

        orderDetailService.increaseQuantityByOrderId(orderId);


        return new CancelOrderResponse(orderId, OrderStatus.CANCELLED);
    }

}
