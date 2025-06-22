package vn.graybee.auth.enums;

import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum Role {
    SUPER_ADMIN, ADMIN, MANAGE, CUSTOMER;

    public static Role fromString(String role, MessageSourceUtil messageSourceUtil) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (RuntimeException e) {
            throw new BusinessCustomException(Constants.Common.role, messageSourceUtil.get("account.role.in_valid", new Object[]{role}));
        }
    }
}
