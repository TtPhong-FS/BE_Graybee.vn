package vn.graybee.modules.account.enums;

import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum Gender {
    MALE, FEMALE, OTHER;

    public static Gender fromString(String gender, MessageSourceUtil messageSourceUtil) {
        try {
            return Gender.valueOf(gender.toUpperCase());
        } catch (RuntimeException e) {
            throw new BusinessCustomException(Constants.Common.gender, messageSourceUtil.get("account.gender.in_valid"));
        }
    }
}
