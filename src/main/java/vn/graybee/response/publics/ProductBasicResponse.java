package vn.graybee.response.publics;

public class ProductBasicResponse {

    private long id;

    private String name;

    private double finalPrice;

    private double price;

    private String thumbnail;

    public ProductBasicResponse(long id, String name, double finalPrice, double price, String thumbnail) {
        this.id = id;
        this.name = name;
        this.finalPrice = finalPrice;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
