package vn.graybee.modules.order.enums;

import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum DeliveryStatus {
    PENDING,
    SHIPPING,
    DELIVERED,
    FAILED,
    COMPLETED,
    RETURNED;

    public static DeliveryStatus fromString(String status, MessageSourceUtil messageSourceUtil) {
        try {
            return DeliveryStatus.valueOf(status.toUpperCase());
        } catch (RuntimeException e) {
            throw new BusinessCustomException(Constants.Order.deliveryStatus, messageSourceUtil.get("order.delivery.status.invalid", new Object[]{status}));
        }
    }
}
