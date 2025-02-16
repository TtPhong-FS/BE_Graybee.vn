package vn.graybee.response.publics;

public class ProductBasicSummaryResponse {

    private long id;

    private String productName;

    private float price;

    private String thumbnail;

    public ProductBasicSummaryResponse(long id, String productName, float price, String thumbnail) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
