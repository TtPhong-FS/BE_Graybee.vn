package vn.graybee.modules.catalog.enums;

import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum CategoryType {
    CATEGORY, SUBCATEGORY, BRAND, TAG;

    public static CategoryType getType(String type, MessageSourceUtil messageSourceUtil) {
        try {
            return CategoryType.valueOf(type.toUpperCase());

        } catch (RuntimeException e) {
            throw new BusinessCustomException(Constants.Common.status, messageSourceUtil.get("catalog.category.type.invalid", new Object[]{type}));
        }

    }
}

