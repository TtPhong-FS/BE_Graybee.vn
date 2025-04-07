package vn.graybee.response.publics.carts;

import java.math.BigDecimal;

public class CartItemResponse {

    private int quantity;

    private BigDecimal total;

    public CartItemResponse() {
    }

    public CartItemResponse(int quantity, BigDecimal total) {
        this.quantity = quantity;
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
