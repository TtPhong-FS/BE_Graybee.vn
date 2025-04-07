package vn.graybee.response.favourites;

import java.math.BigDecimal;

public class ProductFavourite {

    private Long id;

    private double finalPrice;

    private String name;

    private String thumbnail;

    public ProductFavourite() {
    }

    public ProductFavourite(long id, BigDecimal finalPrice, String name, String thumbnail) {
        this.id = id;
        this.finalPrice = finalPrice != null ? finalPrice.doubleValue() : 0.0;
        this.name = name;
        this.thumbnail = thumbnail;
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
