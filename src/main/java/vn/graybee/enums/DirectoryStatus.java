package vn.graybee.enums;

import vn.graybee.constants.ConstantGeneral;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.utils.MessageSourceUtil;

public enum DirectoryStatus {

    ACTIVE("ACTIVE", "Hiển thị"),
    INACTIVE("INACTIVE", "Ẩn"),
    DELETED("DELETED", "Đã xóa"),
    REMOVED("REMOVED", "Xoá tạm thời"),
    PENDING("PENDING", "Chờ duyệt");

    private final String code;

    private final String displayName;

    DirectoryStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static DirectoryStatus getStatus(String status, MessageSourceUtil messageSourceUtil) {
        try {
            return DirectoryStatus.valueOf(status.toUpperCase());
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
