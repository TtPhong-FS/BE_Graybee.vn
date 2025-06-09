package vn.graybee.modules.catalog.enums;

import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum InputType {
    text,
    number,
    select,
    textarea,
    multiple_select;

    public static String getType(String type, MessageSourceUtil messageSourceUtil) {
        try {
            return InputType.valueOf(type.toLowerCase()).name();
        } catch (RuntimeException e) {
            throw new BusinessCustomException(Constants.Common.status, messageSourceUtil.get("catalog.attribute.inputType.invalid", new Object[]{type}));
        }

    }

}
