package vn.graybee.modules.order.enums;

import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum PaymentStatus {
    UNPAID,
    PENDING,
    FAILED,
    REFUNDED,
    PAID;

    public static PaymentStatus fromString(String status, MessageSourceUtil messageSourceUtil) {
        try {
            return PaymentStatus.valueOf(status.toUpperCase());
        } catch (RuntimeException e) {
            throw new BusinessCustomException(Constants.Order.paymentStatus, messageSourceUtil.get("order.payment.status.invalid", new Object[]{status}));
        }
    }
}
