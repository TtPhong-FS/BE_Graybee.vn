package vn.graybee.modules.product.enums;

import vn.graybee.common.Constants;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;

public enum ProductStatus {
    DRAFT("DRAFT", "Nháp"),
    INACTIVE("INACTIVE", "Ngừng kinh doanh"),
    OUT_OF_STOCK("OUT_OF_STOCK", "Hết hàng"),
    REMOVED("REMOVED", "Xoá tạm thời"),
    DELETED("DELETED", "Xoá vĩnh viễn"),
    PUBLISHED("PUBLISHED", "Đã xuất bản"),
    PENDING("PENDING", "Chờ duyệt"),
    COMING_SOON("COMING_SOON", "Sắp ra mắt");

    private final String code;

    private final String displayName;

    ProductStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static ProductStatus getStatus(String status, MessageSourceUtil messageSourceUtil) {
        try {
            return ProductStatus.valueOf(status.toUpperCase());
        } catch (RuntimeException ex) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("common.status.invalid", new Object[]{status.toUpperCase()}));
        }
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
