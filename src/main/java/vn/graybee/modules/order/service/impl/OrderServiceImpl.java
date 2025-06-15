package vn.graybee.modules.order.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.dto.response.AddressResponse;
import vn.graybee.modules.account.service.AddressService;
import vn.graybee.modules.account.service.CustomerService;
import vn.graybee.modules.cart.model.CartItem;
import vn.graybee.modules.cart.service.CartItemService;
import vn.graybee.modules.cart.service.CartService;
import vn.graybee.modules.order.dto.request.OrderCreateRequest;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.user.OrderBasicDto;
import vn.graybee.modules.order.dto.response.user.OrderHistoryResponse;
import vn.graybee.modules.order.enums.OrderStatus;
import vn.graybee.modules.order.model.Order;
import vn.graybee.modules.order.model.OrderDetail;
import vn.graybee.modules.order.repository.OrderRepository;
import vn.graybee.modules.order.service.DeliveryService;
import vn.graybee.modules.order.service.OrderDetailService;
import vn.graybee.modules.order.service.OrderService;
import vn.graybee.modules.order.service.PaymentService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public OrderServiceImpl(MessageSourceUtil messageSourceUtil, OrderRepository orderRepository, DeliveryService deliveryService, OrderDetailService orderDetailService, PaymentService paymentService, AddressService addressService, CartItemService cartItemService, CartService cartService, CustomerService customerService) {
        this.messageSourceUtil = messageSourceUtil;
        this.orderRepository = orderRepository;
        this.deliveryService = deliveryService;
        this.orderDetailService = orderDetailService;
        this.paymentService = paymentService;
        this.addressService = addressService;
        this.cartItemService = cartItemService;
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @Override
    public BasicMessageResponse<List<OrderHistoryResponse>> getOrderHistoryByAccountId(Long accountId, String status) {
        OrderStatus orderStatus = null;

        if (status != null && !status.isEmpty() && !"all".equals(status) && !"null".equals(status)) {
            orderStatus = OrderStatus.fromString(status, messageSourceUtil);
        }


        List<OrderBasicDto> orders = orderRepository.findAllOrdersByAccountIdAndStatus(accountId, orderStatus);

        if (orders.isEmpty()) {
            return new BasicMessageResponse<>(200, messageSourceUtil.get(""), Collections.emptyList());
        }

        List<Long> orderIds = orders.stream().map(OrderBasicDto::getId).toList();

//        List<OrderMapOrderDetailWithProductBasicDto> orderIdAndProducts = orderDetailRepository.getOrderDetailWithProductBasicByOrderIds(orderIds);
//
//        Map<Long, List<OrderDetailProductDto>> orderMapOrderDetail = orderIdAndProducts.stream()
//                .collect(Collectors.groupingBy(OrderMapOrderDetailWithProductBasicDto::getOrderId,
//                        Collectors.mapping(orderDetail -> new OrderDetailProductDto(orderDetail.getOrderDetailId(), orderDetail.getProductId(), orderDetail.getName(), orderDetail.getThumbnail(), orderDetail.getQuantity(), orderDetail.getSubtotal(), orderDetail.getPriceAtTime()), Collectors.toList())));
//
//        Map<Long, Integer> orderTotalQuantityMap = orderDetailRepository.getTotalQuantityByOrderIds(orderIds)
//                .stream()
//                .collect(Collectors.toMap(OrderTotalQuantityDto::getOrderId, OrderTotalQuantityDto::getTotalQuantity));
//
//        List<OrderPaymentMethodDto> orderPaymentMethods = paymentRepository.getPaymentMethodByOrderIds(orderIds);
//
//        Map<Long, PaymentMethod> orderPaymentMethodMap = orderPaymentMethods.stream()
//                .collect(Collectors.toMap(OrderPaymentMethodDto::getOrderId, OrderPaymentMethodDto::getPaymentMethod));
//
//        List<OrderDeliveryTypeDto> orderDeliveries = deliveryRepository.findDeliveryTypeByOrderIds(orderIds);
//        Map<Long, DeliveryType> orderDeliveryMap = orderDeliveries.stream()
//                .collect(Collectors.toMap(OrderDeliveryTypeDto::getOrderId, OrderDeliveryTypeDto::getDeliveryType));
//
        List<OrderHistoryResponse> response = new ArrayList<>();

//        orders.forEach(order -> {
//
//            int totalQuantity = orderTotalQuantityMap.getOrDefault(order.getId(), 0);
//            PaymentMethod paymentMethod = orderPaymentMethodMap.getOrDefault(order.getId(), PaymentMethod.COD);
//            DeliveryType deliveryType = orderDeliveryMap.getOrDefault(order.getId(), DeliveryType.HOME_DELIVERY);
//            response.add(new OrderHistoryResponse(
//                    order.getId(),
//                    order.getStatus(),
//                    order.getTotalAmount(),
//                    totalQuantity,
//                    order.getOrderDate(),
//                    orderMapOrderDetail.getOrDefault(order.getId(), Collections.emptyList()),
//                    deliveryType,
//                    paymentMethod
//            ));
//
//        });

        return new BasicMessageResponse<>(200, "", response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void transformOrdersToAccountBySessionId(Long accountId, String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) return;

        List<Long> orderIds = orderRepository.findIdsBySessionId(sessionId);

        if (!orderIds.isEmpty()) {
            orderRepository.transformOrdersToAccountByIds(accountId, orderIds);
        }

    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<?> createOrder(OrderCreateRequest request, Long accountId, String sessionId) {

        if (accountId == null && sessionId == null) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("common.request.invalid")
            );
        }

        Integer cartId = cartService.getCartIdByAccountIdOrSessionId(accountId, sessionId);

        List<CartItem> cartItems = cartItemService.getCartItemByIdsAndCartId(request.getCartItemIds(), cartId);

        if (cartItems.isEmpty()) {
            throw new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("cart.item.empty"));
        }

        Order order = new Order();

        order = orderRepository.save(order);

        if (accountId != null) {
            order.setAccountId(accountId);
            order.setSessionId(null);
        } else {
            order.setSessionId(sessionId);
            order.setAccountId(null);
        }

        order.setNote(request.getNote());

        List<OrderDetail> orderDetails = orderDetailService.createOrderDetail(order.getId(), cartItems);

        BigDecimal totalAmount = orderDetails.stream()
                .map(OrderDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        order.setStatus(OrderStatus.PENDING);

        order = orderRepository.save(order);

        paymentService.savePaymentByOrderId(order.getId(), order.getTotalAmount(), request.getPaymentCreateRequest());

        AddressResponse address;
        if (accountId != null) {
            Long customerId = customerService.getIdByAccountId(accountId);
            if (request.isUseExistingAddress() && request.getAddressId() != null) {
                address = addressService.getAddressDefaultByIdAndCustomerId(customerId, request.getAddressId());
            } else {
                address = addressService.createAddress(customerId, request.getAddressCreateRequest()).getData();
            }
            deliveryService.createDeliveryForUser(order.getId(), address, request.getDeliveryCreateRequest());
        } else {
            deliveryService.createDeliveryForGuest(order.getId(), request.getAddressCreateRequest(), request.getDeliveryCreateRequest());
        }

        List<Integer> cartItemIds = cartItemService.clearCartItemsByIds(request.getCartItemIds(), cartId);

        cartService.updateCartTotal(cartId);

        return new BasicMessageResponse<>(201, "", cartItemIds);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CancelOrderResponse cancelOrderById(long orderId) {

        if (!orderRepository.checkExistsById(orderId)) {
            throw new BusinessCustomException(Constants.Common.global, "Đơn hàng không tồn tại");
        }

        OrderStatus currentStatus = orderRepository.findStatusById(orderId);

        if (currentStatus == OrderStatus.PENDING || currentStatus == OrderStatus.CONFIRMED || currentStatus == OrderStatus.PROCESSING) {
            orderRepository.updateStatusByIdAndStatus(orderId, OrderStatus.CANCELLED);
        } else {
            throw new BusinessCustomException(Constants.Common.global, "Không thể huỷ, đơn hàng đã được giao cho vận chuyển.");
        }

        return new CancelOrderResponse(orderId, OrderStatus.CANCELLED);
    }

}
