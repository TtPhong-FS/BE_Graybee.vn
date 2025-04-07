package vn.graybee.serviceImps.orders;

import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.orders.CartItem;
import vn.graybee.models.orders.Order;
import vn.graybee.models.orders.OrderDetail;
import vn.graybee.repositories.events.DiscountRepository;
import vn.graybee.repositories.orders.CartItemRepository;
import vn.graybee.repositories.orders.DeliveryRepository;
import vn.graybee.repositories.orders.OrderDetailRepository;
import vn.graybee.repositories.orders.OrderRepository;
import vn.graybee.repositories.orders.PaymentRepository;
import vn.graybee.repositories.products.InventoryRepository;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.requests.orders.OrderCreateRequest;
import vn.graybee.response.orders.OrderBasicDto;
import vn.graybee.response.orders.OrderDeliveryTypeDto;
import vn.graybee.response.orders.OrderDetailProductDto;
import vn.graybee.response.orders.OrderHistoryResponse;
import vn.graybee.response.orders.OrderMapOrderDetailWithProductBasicDto;
import vn.graybee.response.orders.OrderPaymentMethodDto;
import vn.graybee.response.orders.OrderTotalQuantityDto;
import vn.graybee.services.orders.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    public OrderServiceImpl(ProductRepository productRepository, CartItemRepository cartItemRepository, InventoryRepository inventoryRepository, OrderRepository orderRepository, DiscountRepository discountRepository, DeliveryRepository deliveryRepository, OrderDetailRepository orderDetailRepository, PaymentRepository paymentRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.inventoryRepository = inventoryRepository;
        this.orderRepository = orderRepository;
        this.discountRepository = discountRepository;
        this.deliveryRepository = deliveryRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public BasicMessageResponse<List<OrderHistoryResponse>> findOrderHistoriesByUserUid(int userUid) {

        List<OrderBasicDto> orders = orderRepository.findOrdersBasicByUserUid(userUid);

        if (orders.isEmpty()) {
            return new BasicMessageResponse<>(200, "Bạn chưa có đơn hàng nào. Hãy tiếp tục mua hàng!", Collections.emptyList());
        }

        List<Long> orderIds = orders.stream().map(OrderBasicDto::getId).toList();

        List<OrderMapOrderDetailWithProductBasicDto> orderIdAndProducts = orderDetailRepository.getOrderDetailWithProductBasicByOrderIds(orderIds);

        Map<Long, List<OrderDetailProductDto>> orderMapOrderDetail = orderIdAndProducts.stream()
                .collect(Collectors.groupingBy(OrderMapOrderDetailWithProductBasicDto::getOrderId,
                        Collectors.mapping(orderDetail -> new OrderDetailProductDto(orderDetail.getId(), orderDetail.getName(), orderDetail.getThumbnail(), orderDetail.getQuantity(), orderDetail.getSubtotal(), orderDetail.getPriceAtTime()), Collectors.toList())));

        Map<Long, Integer> orderTotalQuantityMap = orderDetailRepository.getTotalQuantityByOrderIds(orderIds)
                .stream()
                .collect(Collectors.toMap(OrderTotalQuantityDto::getOrderId, OrderTotalQuantityDto::getTotalQuantity));

        List<OrderPaymentMethodDto> orderPaymentMethods = paymentRepository.getPaymentMethodByOrderIds(orderIds);

        Map<Long, String> orderPaymentMethodMap = orderPaymentMethods.stream()
                .collect(Collectors.toMap(OrderPaymentMethodDto::getOrderId, OrderPaymentMethodDto::getPaymentMethod));

        List<OrderDeliveryTypeDto> orderDeliveries = deliveryRepository.findDeliveryTypeByOrderIds(orderIds);
        Map<Long, String> orderDeliveryMap = orderDeliveries.stream()
                .collect(Collectors.toMap(OrderDeliveryTypeDto::getOrderId, OrderDeliveryTypeDto::getDeliveryType));

        List<OrderHistoryResponse> response = new ArrayList<>();

        orders.forEach(order -> {

            int totalQuantity = orderTotalQuantityMap.getOrDefault(order.getId(), 0);
            String paymentMethod = orderPaymentMethodMap.getOrDefault(order.getId(), "COD");
            String deliveryType = orderDeliveryMap.getOrDefault(order.getId(), "HOME_DELIVERY");
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

        return new BasicMessageResponse<>(200, "Lấy lịch sử đơn hàng thành công", response);
    }

    @Override
    public BasicMessageResponse<List<OrderHistoryResponse>> findOrderHistoryByUserUidAndStatus(int userUid, String status) {

        List<OrderBasicDto> orders = orderRepository.findOrdersBasicByUserUidAndStatus(userUid, status);

        if (orders.isEmpty()) {
            return new BasicMessageResponse<>(200, "Bạn chưa có đơn hàng nào. Hãy tiếp tục mua hàng!", Collections.emptyList());
        }

        List<Long> orderIds = orders.stream().map(OrderBasicDto::getId).toList();

        List<OrderMapOrderDetailWithProductBasicDto> orderIdAndProducts = orderDetailRepository.getOrderDetailWithProductBasicByOrderIds(orderIds);

        Map<Long, List<OrderDetailProductDto>> orderMapOrderDetail = orderIdAndProducts.stream()
                .collect(Collectors.groupingBy(OrderMapOrderDetailWithProductBasicDto::getOrderId,
                        Collectors.mapping(orderDetail -> new OrderDetailProductDto(orderDetail.getId(), orderDetail.getName(), orderDetail.getThumbnail(), orderDetail.getQuantity(), orderDetail.getSubtotal(), orderDetail.getPriceAtTime()), Collectors.toList())));

        Map<Long, Integer> orderTotalQuantityMap = orderDetailRepository.getTotalQuantityByOrderIds(orderIds)
                .stream()
                .collect(Collectors.toMap(OrderTotalQuantityDto::getOrderId, OrderTotalQuantityDto::getTotalQuantity));

        List<OrderPaymentMethodDto> orderPaymentMethods = paymentRepository.getPaymentMethodByOrderIds(orderIds);

        Map<Long, String> orderPaymentMethodMap = orderPaymentMethods.stream()
                .collect(Collectors.toMap(OrderPaymentMethodDto::getOrderId, OrderPaymentMethodDto::getPaymentMethod));

        List<OrderDeliveryTypeDto> orderDeliveries = deliveryRepository.findDeliveryTypeByOrderIds(orderIds);
        Map<Long, String> orderDeliveryMap = orderDeliveries.stream()
                .collect(Collectors.toMap(OrderDeliveryTypeDto::getOrderId, OrderDeliveryTypeDto::getDeliveryType));

        List<OrderHistoryResponse> response = new ArrayList<>();

        orders.forEach(order -> {

            int totalQuantity = orderTotalQuantityMap.getOrDefault(order.getId(), 0);
            String paymentMethod = orderPaymentMethodMap.getOrDefault(order.getId(), "COD");
            String deliveryType = orderDeliveryMap.getOrDefault(order.getId(), "HOME_DELIVERY");
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

        return new BasicMessageResponse<>(200, "Lấy lịch sử đơn hàng thành công", response);
    }

    @Override
    public BasicMessageResponse<?> createOrder(OrderCreateRequest request, int userUid) {

        int discountId = discountRepository.findByCode(request.getDiscountCode())
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, "Mã giảm giá không tồn tại."));

        List<CartItem> cartItems = cartItemRepository.findByIds(request.getCartItemIds());

        List<OrderDetail> orderDetails = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            int stockQuantity = inventoryRepository.findStockByProductId(item.getProductId());
            if (stockQuantity < item.getQuantity()) {
                throw new BusinessCustomException(ConstantGeneral.general, "Sản phẩm hiện không đủ hàng.");
            }

            BigDecimal priceAtTime = productRepository.findFinalPriceById(item.getProductId());
            BigDecimal subtotal = priceAtTime.multiply(BigDecimal.valueOf(item.getQuantity()));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setPriceAtTime(priceAtTime);
            orderDetail.setSubtotal(subtotal);

            orderDetails.add(orderDetail);

            totalAmount = totalAmount.add(subtotal);
        }

        Order order = new Order();

        order.setNote(request.getNote());
        order.setIssueInvoices(request.isIssueInvoices());
        order.setDiscountId(discountId);
        order.setTotalAmount(totalAmount);

        order = orderRepository.save(order);

        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrderId(order.getId());
        }

        orderDetailRepository.saveAll(orderDetails);
        return null;
    }

}
