package vn.graybee.modules.product.dto.response;

import java.math.BigDecimal;

public class FavoriteProductResponse {

    private Long productId;

    private double price;

    private double finalPrice;

    private String productName;

    private String thumbnailUrl;

    public FavoriteProductResponse() {
    }

    public FavoriteProductResponse(Long productId, BigDecimal price, BigDecimal finalPrice, String productName, String thumbnailUrl) {
        this.productId = productId;
        this.price = price != null ? price.doubleValue() : 0.0;
        this.finalPrice = finalPrice != null ? finalPrice.doubleValue() : 0.0;
        this.productName = productName;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
