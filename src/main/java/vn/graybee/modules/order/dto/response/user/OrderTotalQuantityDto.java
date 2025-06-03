package vn.graybee.modules.order.dto.response.user;

public class OrderTotalQuantityDto {

    private long orderId;

    private Integer totalQuantity;

    public OrderTotalQuantityDto() {
    }

    public OrderTotalQuantityDto(Long orderId, Number totalQuantity) {
        this.orderId = orderId;
        this.totalQuantity = totalQuantity != null ? totalQuantity.intValue() : 0;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

}
