package vn.graybee.response.admin.products;

public class ProductPreviewInOrderHistory {

    private long productId;

    private String name;

    private String thumbnail;

    private double priceAtTime;

    public ProductPreviewInOrderHistory() {
    }

    public ProductPreviewInOrderHistory(long productId, String name, String thumbnail, double priceAtTime) {
        this.productId = productId;
        this.name = name;
        this.thumbnail = thumbnail;
        this.priceAtTime = priceAtTime;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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

    public double getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(double priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

}
