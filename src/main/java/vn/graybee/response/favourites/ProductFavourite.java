package vn.graybee.response.favourites;

import java.math.BigDecimal;

public class ProductFavourite {

    private Long id;

    private double price;

    private double finalPrice;

    private String name;

    private String thumbnail;

    public ProductFavourite() {
    }

    public ProductFavourite(long id, BigDecimal price, BigDecimal finalPrice, String name, String thumbnail) {
        this.id = id;
        this.price = price != null ? price.doubleValue() : 0.0;
        this.finalPrice = finalPrice != null ? finalPrice.doubleValue() : 0.0;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
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

}
