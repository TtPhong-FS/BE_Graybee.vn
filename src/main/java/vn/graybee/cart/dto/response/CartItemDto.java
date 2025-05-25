package vn.graybee.cart.dto.response;

import vn.graybee.response.publics.products.ProductBasicResponse;

import java.math.BigDecimal;

public class CartItemDto {

    private Integer cartItemId;

    private ProductBasicResponse product;

    private int quantity;

    private BigDecimal total;

    public CartItemDto() {
    }

    public CartItemDto(Integer cartItemId, int quantity, BigDecimal total, ProductBasicResponse product) {
        this.cartItemId = cartItemId;
        this.product = product;
        this.quantity = quantity;
        this.total = total;
    }

    public Integer getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Integer cartItemId) {
        this.cartItemId = cartItemId;
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
