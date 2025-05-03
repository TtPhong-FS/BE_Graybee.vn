package vn.graybee.enums;

public enum DirectoryStatus {
    ACTIVE("ACTIVE", "Hiển thị"),
    INACTIVE("INACTIVE", "Ẩn"),
    DELETED("DELETED", "Đã xóa"),
    REMOVED("REMOVED", "Xoá tạm thời"),
    ;

    private final String code;

    private final String displayName;

    DirectoryStatus(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
