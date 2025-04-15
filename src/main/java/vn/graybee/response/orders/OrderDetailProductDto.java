package vn.graybee.response.orders;

import java.math.BigDecimal;

public class OrderDetailProductDto {

    private long orderDetailId;

    private long productId;

    private String productName;

    private String thumbnail;

    private int quantity;

    private BigDecimal subTotal;

    private BigDecimal priceAtTime;

    public OrderDetailProductDto() {
    }

    public OrderDetailProductDto(long orderDetailId, long productId, String productName, String thumbnail, int quantity, BigDecimal subTotal, BigDecimal priceAtTime) {
        this.orderDetailId = orderDetailId;
        this.productId = productId;
        this.productName = productName;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
        this.subTotal = subTotal;
        this.priceAtTime = priceAtTime;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(BigDecimal priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

}
