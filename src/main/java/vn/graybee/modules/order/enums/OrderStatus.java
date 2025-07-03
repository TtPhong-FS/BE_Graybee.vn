package vn.graybee.modules.order.enums;

import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum OrderStatus {
    PENDING,
    CONFIRMED,
    PROCESSING,
    DELIVERED,
    COMPLETED,
    RETURNED,
    CANCELLED;

    public static OrderStatus fromString(String status, MessageSourceUtil messageSourceUtil) {
        try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (RuntimeException ex) {
            throw new BusinessCustomException(Constants.Common.status, messageSourceUtil.get("common.status.invalid", new Object[]{status.toUpperCase()}));
        }
    }

}
