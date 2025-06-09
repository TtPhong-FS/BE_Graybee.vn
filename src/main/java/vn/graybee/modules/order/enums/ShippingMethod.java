package vn.graybee.modules.order.enums;

import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum ShippingMethod {
    STANDARD_SHIPPING,
    ECONOMY_SHIPPING,
    FAST_DELIVERY;

    public static ShippingMethod fromString(String method, MessageSourceUtil messageSourceUtil) {
        try {
            return ShippingMethod.valueOf(method.toUpperCase());
        } catch (RuntimeException e) {
            throw new BusinessCustomException(Constants.Order.shippingMethod, messageSourceUtil.get("order.shipping_method.invalid", new Object[]{method}));
        }
    }
}
