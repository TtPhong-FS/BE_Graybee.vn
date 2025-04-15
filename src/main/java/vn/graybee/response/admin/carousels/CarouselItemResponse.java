package vn.graybee.response.admin.carousels;

import java.math.BigDecimal;

public class CarouselItemResponse {

    private Integer id;

    private String carouselGroupType;

    private Long productId;

    private String productName;

    private BigDecimal price;

    private BigDecimal finalPrice;

    private String thumbnail;

    private int position;

    private boolean active;


    public CarouselItemResponse() {
    }

    public CarouselItemResponse(Integer id, String carouselGroupType, Long productId, String productName, BigDecimal price, BigDecimal finalPrice, String thumbnail, int position, boolean active) {
        this.id = id;
        this.carouselGroupType = carouselGroupType;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.finalPrice = finalPrice;
        this.thumbnail = thumbnail;
        this.position = position;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarouselGroupType() {
        return carouselGroupType;
    }

    public void setCarouselGroupType(String carouselGroupType) {
        this.carouselGroupType = carouselGroupType;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
