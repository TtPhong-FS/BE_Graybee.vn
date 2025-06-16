package vn.graybee.modules.order.service.impl;

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
import vn.graybee.modules.order.dto.request.OrderCreateRequest;
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

import java.math.BigDecimal;
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
        }

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public CancelOrderResponse cancelOrderByCode(String code) {
        long id = orderRepository.findIdByCode(code)
                .orElseThrow(() -> new BusinessCustomException(Constants.Common.global, "Không tìm thấy đơn hàng trong hệ thống"));

        cancelOrderById(id);

        return new CancelOrderResponse(id, OrderStatus.CANCELLED);
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
        order.setTotalAmount(BigDecimal.ZERO);
        order = orderRepository.save(order);

        List<OrderDetail> orderDetails = orderDetailService.createOrderDetail(order.getId(), cartItems);

        BigDecimal totalAmount = orderDetails.stream()
                .map(OrderDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);

        paymentService.savePaymentByOrderId(order.getId(), order.getTotalAmount(), request.getPaymentMethod());

        if (request.getAddressId() != null && accountId != null) {
            Long customerId = customerService.getIdByAccountId(accountId);
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

        if (currentStatus == OrderStatus.PENDING || currentStatus == OrderStatus.CONFIRMED || currentStatus == OrderStatus.PROCESSING) {
            orderRepository.updateStatusByIdAndStatus(orderId, OrderStatus.CANCELLED);
        } else {
            throw new BusinessCustomException(Constants.Common.global, "Không thể huỷ, đơn hàng đã được giao cho vận chuyển.");
        }

        return new CancelOrderResponse(orderId, OrderStatus.CANCELLED);
    }

}
