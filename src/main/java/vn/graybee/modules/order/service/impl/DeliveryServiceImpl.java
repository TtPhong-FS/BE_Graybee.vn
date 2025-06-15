package vn.graybee.modules.order.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.AddressUtil;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.dto.request.AddressCreateRequest;
import vn.graybee.modules.account.dto.response.AddressResponse;
import vn.graybee.modules.order.dto.request.DeliveryCreateRequest;
import vn.graybee.modules.order.dto.response.admin.delivery.DeliveryIdStatusResponse;
import vn.graybee.modules.order.enums.DeliveryStatus;
import vn.graybee.modules.order.enums.DeliveryType;
import vn.graybee.modules.order.enums.ShippingMethod;
import vn.graybee.modules.order.model.Delivery;
import vn.graybee.modules.order.repository.DeliveryRepository;
import vn.graybee.modules.order.service.DeliveryService;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final MessageSourceUtil messageSourceUtil;

    private final DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(MessageSourceUtil messageSourceUtil, DeliveryRepository deliveryRepository) {
        this.messageSourceUtil = messageSourceUtil;
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Delivery createDeliveryForUser(Long orderId, AddressResponse address, DeliveryCreateRequest request) {
        ShippingMethod shippingMethod = ShippingMethod.fromString(request.getShippingMethod(), messageSourceUtil);
        DeliveryType deliveryType = DeliveryType.fromString(request.getDeliveryType(), messageSourceUtil);

        int extraDays = new Random().nextInt(10) + 2;

        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setRecipientName(address.getRecipientName());
        delivery.setRecipientPhone(address.getPhone());

        delivery.setDeliveryType(deliveryType);

        if (deliveryType.equals(DeliveryType.STORE_PICKUP)) {
            delivery.setStatus(DeliveryStatus.DELIVERED);
        } else {
            delivery.setStatus(DeliveryStatus.PENDING);
        }
        delivery.setShippingAddress(
                AddressUtil.formatFullAddress(address.getStreet(), address.getCommune(), address.getDistrict(), address.getCity())
        );
        delivery.setShippingMethod(shippingMethod);
        delivery.setEstimatedDeliveryDate(LocalDate.now().plusDays(extraDays));

        return deliveryRepository.save(delivery);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Delivery createDeliveryForGuest(Long orderId, AddressCreateRequest addressCreateRequest, DeliveryCreateRequest request) {
        ShippingMethod shippingMethod = ShippingMethod.fromString(request.getShippingMethod(), messageSourceUtil);
        DeliveryType deliveryType = DeliveryType.fromString(request.getDeliveryType(), messageSourceUtil);

        int extraDays = new Random().nextInt(10) + 2;

        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setRecipientName(addressCreateRequest.getRecipientName());
        delivery.setRecipientPhone(addressCreateRequest.getPhone());

        delivery.setDeliveryType(deliveryType);

        if (deliveryType.equals(DeliveryType.STORE_PICKUP)) {
            delivery.setStatus(DeliveryStatus.DELIVERED);
        } else {
            delivery.setStatus(DeliveryStatus.PENDING);
        }
        delivery.setShippingAddress(
                AddressUtil.formatFullAddress(addressCreateRequest.getStreet(), addressCreateRequest.getCommune(), addressCreateRequest.getDistrict(), addressCreateRequest.getCity())
        );
        delivery.setShippingMethod(shippingMethod);
        delivery.setEstimatedDeliveryDate(LocalDate.now().plusDays(extraDays));

        return deliveryRepository.save(delivery);
    }

    @Override
    public List<Delivery> getAllDelivery() {
        return deliveryRepository.findAll();
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public DeliveryIdStatusResponse updateDeliveryStatusById(long id, String status) {
        if (!deliveryRepository.existsById(id)) {
            throw new BusinessCustomException(Constants.Common.global, "Thông tin vận chuyển không tồn tại");
        }

        DeliveryStatus deliveryStatus = DeliveryStatus.fromString(status, messageSourceUtil);

        deliveryRepository.updateStatusByIdAndStatus(id, deliveryStatus);

        return new DeliveryIdStatusResponse(id, deliveryStatus);
    }

}
