package vn.graybee.order.dto.response;

import java.math.BigDecimal;

public class OrderDetailDto {

    private Long productId;

    private int totalQuantity;

    private BigDecimal priceAtTime;

    private BigDecimal subtotal;

    public OrderDetailDto() {
    }

    public OrderDetailDto(Long productId, int totalQuantity, BigDecimal priceAtTime, BigDecimal subtotal) {
        this.productId = productId;
        this.totalQuantity = totalQuantity;
        this.priceAtTime = priceAtTime;
        this.subtotal = subtotal;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(BigDecimal priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

}
