package vn.graybee.modules.catalog.enums;

import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum CategoryStatus {
    ACTIVE("ACTIVE", "Hiển thị"),
    INACTIVE("INACTIVE", "Ẩn"),
    REMOVED("REMOVED", "Xoá bị tạm"),
    PENDING("PENDING", "Chờ duyệt");

    private final String code;

    private final String displayName;

    CategoryStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static CategoryStatus getStatus(String status, MessageSourceUtil messageSourceUtil) {
        try {
            return CategoryStatus.valueOf(status.toUpperCase());
        } catch (RuntimeException ex) {
            throw new BusinessCustomException(ConstantGeneral.status, messageSourceUtil.get("common.status.invalid", new Object[]{status.toUpperCase()}));
        }
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
