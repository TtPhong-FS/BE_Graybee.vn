package vn.graybee.response.publics.carts;

import vn.graybee.response.publics.products.ProductBasicResponse;

import java.math.BigDecimal;

public class AddItemToCartResponse {

    private int id;

    private ProductBasicResponse product;

    private int quantity;

    private BigDecimal total;

    public AddItemToCartResponse() {
    }

    public AddItemToCartResponse(int id, ProductBasicResponse product, int quantity, BigDecimal total) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductBasicResponse getProduct() {
        return product;
    }

    public void setProduct(ProductBasicResponse product) {
        this.product = product;
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
