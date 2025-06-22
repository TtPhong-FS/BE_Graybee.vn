package vn.graybee.modules.order.service;

import vn.graybee.modules.account.model.Address;
import vn.graybee.modules.order.dto.request.CustomerInfoRequest;
import vn.graybee.modules.order.dto.request.ShippingInfoRequest;
import vn.graybee.modules.order.dto.response.DeliveryDto;
import vn.graybee.modules.order.dto.response.admin.delivery.DeliveryIdStatusResponse;
import vn.graybee.modules.order.model.Delivery;

import java.util.List;

public interface DeliveryService {

    List<Delivery> getAllDelivery();

    DeliveryIdStatusResponse updateDeliveryStatusById(long id, String status);

    void createDeliveryForCustomer(Long id, ShippingInfoRequest shippingInfo, Address address);

    void createDeliveryForGuest(Long id, CustomerInfoRequest customerInfo, ShippingInfoRequest shippingInfo);

    DeliveryDto findDeliveryDtoByOrderId(long orderId);

}
