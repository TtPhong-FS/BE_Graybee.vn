package vn.graybee.modules.order.dto.response.user;

import java.math.BigDecimal;

public class OrderMapOrderDetailWithProductBasicDto {

    private long orderId;

    private long orderDetailId;

    private long productId;

    private String name;

    private String thumbnail;

    private int quantity;

    private BigDecimal subtotal;

    private BigDecimal priceAtTime;

    public OrderMapOrderDetailWithProductBasicDto() {
    }

    public OrderMapOrderDetailWithProductBasicDto(long orderId, long orderDetailId, long productId, String name, String thumbnail, int quantity, BigDecimal subtotal, BigDecimal priceAtTime) {
        this.orderId = orderId;
        this.orderDetailId = orderDetailId;
        this.productId = productId;
        this.name = name;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.priceAtTime = priceAtTime;
    }

    public long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public BigDecimal getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(BigDecimal priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

}
