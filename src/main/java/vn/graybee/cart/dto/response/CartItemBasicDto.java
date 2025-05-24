package vn.graybee.cart.dto.response;

import java.math.BigDecimal;

public class CartItemBasicDto {

    private int id;

    private int quantity;

    private BigDecimal total;

    public CartItemBasicDto() {
    }

    public CartItemBasicDto(int id, int quantity, BigDecimal total) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
