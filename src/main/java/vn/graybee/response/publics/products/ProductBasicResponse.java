package vn.graybee.response.publics.products;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductBasicResponse implements Serializable {

    private long id;

    private String name;

    private BigDecimal price;

    private BigDecimal finalPrice;

    private String thumbnail;

    public ProductBasicResponse() {
    }

    public ProductBasicResponse(long id, String name, BigDecimal price, BigDecimal finalPrice, String thumbnail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.finalPrice = finalPrice;
        this.thumbnail = thumbnail;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
