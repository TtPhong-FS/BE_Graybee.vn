package vn.graybee.modules.order.service;

import vn.graybee.modules.account.dto.request.AddressCreateRequest;
import vn.graybee.modules.account.dto.response.AddressResponse;
import vn.graybee.modules.order.dto.request.DeliveryCreateRequest;
import vn.graybee.modules.order.model.Delivery;

public interface DeliveryService {

    Delivery createDeliveryForUser(Long orderId, AddressResponse address, DeliveryCreateRequest request);

    Delivery createDeliveryForGuest(Long orderId, AddressCreateRequest addressCreateRequest, DeliveryCreateRequest request);

}
