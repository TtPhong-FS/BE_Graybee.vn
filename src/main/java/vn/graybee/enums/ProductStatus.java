package vn.graybee.enums;

public enum ProductStatus {
    DRAFT("DRAFT", "Soạn thảo"),
    INACTIVE("INACTIVE", "Ngừng kinh doanh"),
    OUT_OF_STOCK("OUT_OF_STOCK", "Hết hàng"),
    REMOVED("REMOVED", "Đã loại bỏ"),
    DELETED("DELETED", "Đã xóa hoàn toàn"),
    PUBLISHED("PUBLISHED", "Đã xuất bản"),
    PENDING("PENDING", "Chờ duyệt"),
    COMING_SOON("COMING_SOON", "Sắp ra mắt");

    private final String code;

    private final String displayName;

    ProductStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static ProductStatus fromCode(String code) {
        for (ProductStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
