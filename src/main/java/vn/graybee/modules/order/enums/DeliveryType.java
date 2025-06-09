package vn.graybee.modules.order.enums;

import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum DeliveryType {
    HOME_DELIVERY,
    STORE_PICKUP;

    public static DeliveryType fromString(String type, MessageSourceUtil messageSourceUtil) {
        try {
            return DeliveryType.valueOf(type.toUpperCase());
        } catch (RuntimeException e) {
            throw new BusinessCustomException(Constants.Order.deliveryType, messageSourceUtil.get("order.delivery.type.invalid", new Object[]{type}));
        }
    }
}
