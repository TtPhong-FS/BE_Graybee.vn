package vn.graybee.order.service;

import vn.graybee.account.dto.request.AddressCreateRequest;
import vn.graybee.account.dto.response.AddressResponse;
import vn.graybee.order.dto.request.DeliveryCreateRequest;
import vn.graybee.order.model.Delivery;

public interface DeliveryService {

    Delivery createDeliveryForUser(Long orderId, AddressResponse address, DeliveryCreateRequest request);

    Delivery createDeliveryForGuest(Long orderId, AddressCreateRequest addressCreateRequest, DeliveryCreateRequest request);

}
