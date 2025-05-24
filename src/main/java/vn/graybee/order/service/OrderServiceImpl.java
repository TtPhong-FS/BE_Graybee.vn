package vn.graybee.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.account.model.Address;
import vn.graybee.account.repository.AddressRepository;
import vn.graybee.cart.entity.CartItem;
import vn.graybee.cart.repository.CartItemRepository;
import vn.graybee.cart.service.CartService;
import vn.graybee.common.constants.ConstantCart;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantInventory;
import vn.graybee.common.constants.ConstantOrder;
import vn.graybee.common.constants.ConstantUser;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.common.dto.PaginationInfo;
import vn.graybee.common.dto.SortInfo;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.AddressUtil;
import vn.graybee.common.utils.CodeGenerator;
import vn.graybee.inventory.repository.InventoryRepository;
import vn.graybee.order.dto.request.OrderCreateRequest;
import vn.graybee.order.dto.response.admin.AdminOrderResponse;
import vn.graybee.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.order.dto.response.admin.ConfirmOrderResponse;
import vn.graybee.order.dto.response.user.OrderBasicDto;
import vn.graybee.order.dto.response.user.OrderDeliveryTypeDto;
import vn.graybee.order.dto.response.user.OrderDetailProductDto;
import vn.graybee.order.dto.response.user.OrderHistoryResponse;
import vn.graybee.order.dto.response.user.OrderMapOrderDetailWithProductBasicDto;
import vn.graybee.order.dto.response.user.OrderPaymentMethodDto;
import vn.graybee.order.dto.response.user.OrderTotalQuantityDto;
import vn.graybee.order.enums.DeliveryStatus;
import vn.graybee.order.enums.DeliveryType;
import vn.graybee.order.enums.OrderStatus;
import vn.graybee.order.enums.PaymentMethod;
import vn.graybee.order.enums.PaymentStatus;
import vn.graybee.order.enums.ShippingMethod;
import vn.graybee.order.model.Delivery;
import vn.graybee.order.model.Order;
import vn.graybee.order.model.OrderDetail;
import vn.graybee.order.model.Payment;
import vn.graybee.order.repository.DeliveryRepository;
import vn.graybee.order.repository.OrderDetailRepository;
import vn.graybee.order.repository.OrderRepository;
import vn.graybee.order.repository.PaymentRepository;
import vn.graybee.product.repository.ProductRepository;
import vn.graybee.repositories.events.DiscountRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    private final InventoryRepository inventoryRepository;

    private final OrderRepository orderRepository;

    private final DiscountRepository discountRepository;

    private final DeliveryRepository deliveryRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final PaymentRepository paymentRepository;

    private final AddressRepository addressRepository;

    private final CartService cartService;

    public OrderServiceImpl(ProductRepository productRepository, CartItemRepository cartItemRepository, InventoryRepository inventoryRepository, OrderRepository orderRepository, DiscountRepository discountRepository, DeliveryRepository deliveryRepository, OrderDetailRepository orderDetailRepository, PaymentRepository paymentRepository, AddressRepository addressRepository, CartService cartService) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.inventoryRepository = inventoryRepository;
        this.orderRepository = orderRepository;
        this.discountRepository = discountRepository;
        this.deliveryRepository = deliveryRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.paymentRepository = paymentRepository;
        this.addressRepository = addressRepository;
        this.cartService = cartService;
    }

    private static Payment getPayment(OrderCreateRequest request, Long orderId, BigDecimal totalAmount) {

        PaymentMethod paymentMethod = request.getPaymentMethodEnum();

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(PaymentStatus.UNPAID);

        payment.setTotalAmount(totalAmount);
        payment.setTransactionId(null);
        payment.setCurrentCode("VND");
        return payment;
    }

    private static Delivery getDelivery(OrderCreateRequest request, Address address, Long orderId) {

        ShippingMethod shippingMethod = request.getShippingMethodEnum();
        DeliveryType deliveryType = request.getDeliveryTypeEnum();

        int extraDays = new Random().nextInt(10) + 2;

        Delivery delivery = new Delivery(LocalDate.now());
        delivery.setOrderId(orderId);

        delivery.setDeliveryType(deliveryType);

        if (deliveryType.equals(DeliveryType.STORE_PICKUP)) {
            delivery.setTrackingNumber(null);
            delivery.setDeliveryStatus(DeliveryStatus.DELIVERED);
        } else {
            delivery.setDeliveryStatus(DeliveryStatus.PENDING);
            delivery.setTrackingNumber(CodeGenerator.generateCode(12, CodeGenerator.ALPHANUMERIC.toUpperCase()));
        }
        delivery.setShippingAddress(
                AddressUtil.formatFullAddress(address.getStreetAddress(), address.getCommune(), address.getDistrict(), address.getCity())
        );
        delivery.setShippingMethod(shippingMethod);
        delivery.setOrderDate(LocalDate.now());
        delivery.setEstimatedDeliveryDate(LocalDate.now().plusDays(extraDays));
        delivery.setUpdatedAt(LocalDateTime.now());

        return delivery;
    }

    @Override
    public BasicMessageResponse<List<OrderHistoryResponse>> findOrderHistoriesByUserUid(String userUid, String status) {
        OrderStatus orderStatus = null;
        try {
            if (status != null && !status.isEmpty() && !"all".equals(status) && !"null".equals(status)) {
                orderStatus = OrderStatus.valueOf(status.toUpperCase());
            }
        } catch (RuntimeException e) {
            throw new BusinessCustomException(ConstantGeneral.status, ConstantGeneral.status_invalid);
        }

        List<OrderBasicDto> orders = orderRepository.findOrdersByUserUidAndStatusOptional(userUid, orderStatus);

        if (orders.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantOrder.order_history_empty, Collections.emptyList());
        }

        List<Long> orderIds = orders.stream().map(OrderBasicDto::getId).toList();

        List<OrderMapOrderDetailWithProductBasicDto> orderIdAndProducts = orderDetailRepository.getOrderDetailWithProductBasicByOrderIds(orderIds);

        Map<Long, List<OrderDetailProductDto>> orderMapOrderDetail = orderIdAndProducts.stream()
                .collect(Collectors.groupingBy(OrderMapOrderDetailWithProductBasicDto::getOrderId,
                        Collectors.mapping(orderDetail -> new OrderDetailProductDto(orderDetail.getOrderDetailId(), orderDetail.getProductId(), orderDetail.getName(), orderDetail.getThumbnail(), orderDetail.getQuantity(), orderDetail.getSubtotal(), orderDetail.getPriceAtTime()), Collectors.toList())));

        Map<Long, Integer> orderTotalQuantityMap = orderDetailRepository.getTotalQuantityByOrderIds(orderIds)
                .stream()
                .collect(Collectors.toMap(OrderTotalQuantityDto::getOrderId, OrderTotalQuantityDto::getTotalQuantity));

        List<OrderPaymentMethodDto> orderPaymentMethods = paymentRepository.getPaymentMethodByOrderIds(orderIds);

        Map<Long, PaymentMethod> orderPaymentMethodMap = orderPaymentMethods.stream()
                .collect(Collectors.toMap(OrderPaymentMethodDto::getOrderId, OrderPaymentMethodDto::getPaymentMethod));

        List<OrderDeliveryTypeDto> orderDeliveries = deliveryRepository.findDeliveryTypeByOrderIds(orderIds);
        Map<Long, DeliveryType> orderDeliveryMap = orderDeliveries.stream()
                .collect(Collectors.toMap(OrderDeliveryTypeDto::getOrderId, OrderDeliveryTypeDto::getDeliveryType));

        List<OrderHistoryResponse> response = new ArrayList<>();

        orders.forEach(order -> {

            int totalQuantity = orderTotalQuantityMap.getOrDefault(order.getId(), 0);
            PaymentMethod paymentMethod = orderPaymentMethodMap.getOrDefault(order.getId(), PaymentMethod.COD);
            DeliveryType deliveryType = orderDeliveryMap.getOrDefault(order.getId(), DeliveryType.HOME_DELIVERY);
            response.add(new OrderHistoryResponse(
                    order.getId(),
                    order.getStatus(),
                    order.getTotalAmount(),
                    totalQuantity,
                    order.getOrderDate(),
                    orderMapOrderDetail.getOrDefault(order.getId(), Collections.emptyList()),
                    deliveryType,
                    paymentMethod
            ));

        });

        return new BasicMessageResponse<>(200, ConstantOrder.success_get_order_history, response);
    }

    @Override
    public MessageResponse<List<AdminOrderResponse>> findAll(int page, int size, String sortBy, String order) {

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

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<?> createOrder(OrderCreateRequest request, String userUid, String sessionId) {

        if (userUid != null && sessionId != null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.conflict_UserUid_and_sessionId);
        }

        Integer cartId = cartService.findByUserIdOrSessionId(userUid, sessionId);
        List<CartItem> cartItems = cartItemRepository.findByIdsAndCartId(request.getCartItemIds(), cartId);

        if (cartItems.isEmpty()) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantCart.item_empty);
        }

        Integer discountId = null;
        if (request.getDiscountCode() != null && !request.getDiscountCode().isEmpty()) {
            discountId = discountRepository.findByCode(request.getDiscountCode())
                    .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.discount_not_exists));
        }

        List<OrderDetail> orderDetails = new ArrayList<>();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            Integer stockQuantity = inventoryRepository.findStockByProductId(item.getProductId());

            if (stockQuantity == null) {
                throw new BusinessCustomException(ConstantGeneral.general, ConstantInventory.product_out_of_stock);
            }

            if (stockQuantity < item.getQuantity()) {
                throw new BusinessCustomException(ConstantGeneral.general, ConstantInventory.product_out_of_stock);
            }

            BigDecimal priceAtTime = productRepository.findFinalPriceById(item.getProductId());
            BigDecimal subtotal = priceAtTime.multiply(BigDecimal.valueOf(item.getQuantity()));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setPriceAtTime(priceAtTime);
            orderDetail.setProductId(item.getProductId());
            orderDetail.setSubtotal(subtotal);

            orderDetails.add(orderDetail);

            totalAmount = totalAmount.add(subtotal);
        }

        Address address = new Address();

        if (request.isUseExistingAddress()) {
            if (request.getAddressId() != null) {
                address = addressRepository.findById(request.getAddressId()).orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantUser.address_not_exists));
            }
        } else {
            address.setCity(request.getCity());
            address.setDistrict(request.getDistrict());
            address.setCommune(request.getCommune());
            address.setStreetAddress(request.getStreetAddress());
            address.setFullName(request.getFullName());
            address.setPhoneNumber(request.getPhoneNumber());

            if (sessionId != null) {
                address.setSessionId(sessionId);
            } else if (userUid != null) {
                address.setUserUid(userUid);
            }
            address.setDefault(false);
            address = addressRepository.save(address);
        }

        Order order = new Order();

        if (userUid != null) {
            order.setUserUid(userUid);
            order.setGuest(false);
            order.setSessionId(null);
        } else if (sessionId != null) {
            order.setSessionId(sessionId);
            order.setGuest(true);
            order.setUserUid(null);
        }

        order.setAddressId(address.getId());
        order.setConfirmed(false);
        order.setCancelled(false);
        order.setNote(request.getNote());
        order.setDiscountId(discountId);
        order.setTotalAmount(totalAmount);
        order.setIssueInvoices(request.isIssueInvoices());

        order.setStatus(OrderStatus.PENDING);

        order = orderRepository.save(order);

        Payment payment = getPayment(request, order.getId(), order.getTotalAmount());
        paymentRepository.save(payment);

        Delivery delivery = getDelivery(request, address, order.getId());
        deliveryRepository.save(delivery);

        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrderId(order.getId());

            inventoryRepository.updateQuantityAfterSuccessOrder(orderDetail.getQuantity(), orderDetail.getProductId());
        }

        orderDetailRepository.saveAll(orderDetails);

        cartItemRepository.deleteByIds(request.getCartItemIds());

        cartService.updateTotalAmount(cartId);

        List<Integer> orderedCartItemIds = request.getCartItemIds();
        return new BasicMessageResponse<>(201, ConstantOrder.success_create_order, orderedCartItemIds);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<ConfirmOrderResponse> confirmOrder(long orderId) {

        if (!orderRepository.checkExistsById(orderId)) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantOrder.does_not_exists);
        }

        orderRepository.confirmOrderById(orderId);

        ConfirmOrderResponse response = new ConfirmOrderResponse(orderId, true, OrderStatus.CONFIRMED);

        return new BasicMessageResponse<>(200, ConstantOrder.success_confirm, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<CancelOrderResponse> cancelOrder(long orderId) {

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

}
