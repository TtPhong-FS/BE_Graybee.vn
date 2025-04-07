package vn.graybee.response.orders;

import java.math.BigDecimal;

public class OrderMapOrderDetailWithProductBasicDto {

    private long orderId;

    private long id;

    private String name;

    private String thumbnail;

    private int quantity;

    private BigDecimal subtotal;

    private BigDecimal priceAtTime;

    public OrderMapOrderDetailWithProductBasicDto() {
    }

    public OrderMapOrderDetailWithProductBasicDto(long orderId, long id, String name, String thumbnail, int quantity, BigDecimal subtotal, BigDecimal priceAtTime) {
        this.orderId = orderId;
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.priceAtTime = priceAtTime;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
