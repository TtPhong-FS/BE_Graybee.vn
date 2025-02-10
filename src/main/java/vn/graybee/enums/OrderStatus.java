package vn.graybee.enums;

public enum OrderStatus {
    PENDING("Đang chờ xử lý"),
    PROCESSING("Đang xử lý"),
    COMPLETED("Đã hoàn thành"),
    CANCELED("Đã huỷ");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
