package vn.graybee.modules.order.enums;

import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum PaymentMethod {
    COD,
    BANKING,
    MOMO,
    VNPAY,
    ZALOPAY;

    public static PaymentMethod fromString(String method, MessageSourceUtil messageSourceUtil) {
        try {
            return PaymentMethod.valueOf(method.toUpperCase());
        } catch (RuntimeException e) {
            throw new BusinessCustomException(Constants.Order.paymentMethod, messageSourceUtil.get("order.payment.method.invalid", new Object[]{method}));
        }
    }
}
