package vn.graybee.modules.catalog.enums;

import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum CategoryType {
    PRODUCT_TYPE, USAGE, PRICE_RANGE, FEATURE, TECH_SPEC, COLOR, MATERIAL;

    public static CategoryType getType(String type, MessageSourceUtil messageSourceUtil) {
        try {
            return CategoryType.valueOf(type.toUpperCase());

        } catch (RuntimeException e) {
            throw new BusinessCustomException(ConstantGeneral.status, messageSourceUtil.get("category.type.invalid", new Object[]{type}));
        }

    }
}

