package vn.graybee.taxonomy.enums;

import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum TaxonomyStatus {
    ACTIVE("ACTIVE", "Hiển thị"),
    INACTIVE("INACTIVE", "Ẩn"),
    DELETED("DELETED", "Đã xóa"),
    REMOVED("REMOVED", "Xoá tạm thời"),
    PENDING("PENDING", "Chờ duyệt");

    private final String code;

    private final String displayName;

    TaxonomyStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static TaxonomyStatus getStatus(String status, MessageSourceUtil messageSourceUtil) {
        try {
            return TaxonomyStatus.valueOf(status.toUpperCase());
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
