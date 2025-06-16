package vn.graybee.modules.order.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.AddressUtil;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.model.Address;
import vn.graybee.modules.order.dto.request.CustomerInfoRequest;
import vn.graybee.modules.order.dto.request.ShippingInfoRequest;
import vn.graybee.modules.order.dto.response.admin.delivery.DeliveryIdStatusResponse;
import vn.graybee.modules.order.enums.DeliveryStatus;
import vn.graybee.modules.order.enums.DeliveryType;
import vn.graybee.modules.order.enums.ShippingMethod;
import vn.graybee.modules.order.model.Delivery;
import vn.graybee.modules.order.repository.DeliveryRepository;
import vn.graybee.modules.order.service.DeliveryService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createDeliveryForCustomer(Long id, ShippingInfoRequest shippingInfo, Address address) {

        ShippingMethod shippingMethod = ShippingMethod.fromString(shippingInfo.getDeliveryMethod(), messageSourceUtil);
        DeliveryType deliveryType = DeliveryType.fromString(shippingInfo.getDeliveryType(), messageSourceUtil);


        Delivery delivery = new Delivery();
        delivery.setRecipientPhone(address.getPhone());
        delivery.setRecipientName(address.getRecipientName());
        delivery.setShippingAddress(AddressUtil.formatFullAddress(address.getStreet(), address.getCommune(), address.getDistrict(), address.getCity()));
        delivery.setDeliveryType(deliveryType);
        delivery.setShippingMethod(shippingMethod);

        if (Objects.equals(shippingInfo.getDeliveryType(), DeliveryType.STORE_PICKUP.name())) {
            delivery.setStatus(DeliveryStatus.COMPLETED);
        } else {
            delivery.setStatus(DeliveryStatus.PENDING);
        }

        int extraDays = new Random().nextInt(10) + 2;
        delivery.setEstimatedDeliveryDate(LocalDate.now().plusDays(extraDays));

        deliveryRepository.save(delivery);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createDeliveryForGuest(Long id, CustomerInfoRequest customerInfo, ShippingInfoRequest shippingInfo) {

        ShippingMethod shippingMethod = ShippingMethod.fromString(shippingInfo.getDeliveryMethod(), messageSourceUtil);
        DeliveryType deliveryType = DeliveryType.fromString(shippingInfo.getDeliveryType(), messageSourceUtil);


        Delivery delivery = new Delivery();
        delivery.setRecipientPhone(customerInfo.getRecipientPhone());
        delivery.setRecipientName(customerInfo.getRecipientName());
        delivery.setShippingAddress(AddressUtil.formatFullAddress(shippingInfo.getStreetAddress(), shippingInfo.getCommune(), shippingInfo.getDistrict(), shippingInfo.getCity()));
        delivery.setDeliveryType(deliveryType);
        delivery.setShippingMethod(shippingMethod);

        if (Objects.equals(shippingInfo.getDeliveryType(), DeliveryType.STORE_PICKUP.name())) {
            delivery.setStatus(DeliveryStatus.COMPLETED);
        } else {
            delivery.setStatus(DeliveryStatus.PENDING);
        }

        int extraDays = new Random().nextInt(10) + 2;
        delivery.setEstimatedDeliveryDate(LocalDate.now().plusDays(extraDays));

        deliveryRepository.save(delivery);
    }


}
