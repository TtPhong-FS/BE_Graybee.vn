package vn.graybee.response.publics.products;

import java.math.BigDecimal;

public class ProductPriceResponse {

    private long id;

    private BigDecimal finalPrice;

    public ProductPriceResponse() {
    }

    public ProductPriceResponse(long id, BigDecimal finalPrice) {
        this.id = id;
        this.finalPrice = finalPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

}
