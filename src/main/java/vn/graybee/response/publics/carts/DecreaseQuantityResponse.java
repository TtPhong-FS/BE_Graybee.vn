package vn.graybee.response.publics.carts;

import java.math.BigDecimal;

public class DecreaseQuantityResponse {

    private int id;

    private int quantity;

    private BigDecimal total;

    public DecreaseQuantityResponse() {
    }


    public DecreaseQuantityResponse(int id, int quantity, BigDecimal total) {
        this.id = id;
        this.quantity = quantity;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
