package vn.graybee.response.publics.products;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ProductDetailResponse {

    private long id;

    private String name;

    private String manufacturerName;

    private int warranty;

    private String conditions;

    private float weight;

    private String color;

    private String thumbnail;

    private List<String> images;

    private BigDecimal price;

    private BigDecimal finalPrice;

    private int discountPercent;

    private String description;

    private List<ReviewCommentDto> reviews;

    public ProductDetailResponse() {
    }

    public ProductDetailResponse(long id, String name, String manufacturerName, int warranty, String conditions, float weight, String color, String thumbnail, BigDecimal price, BigDecimal finalPrice, int discountPercent) {
        this.id = id;
        this.name = name;
        this.manufacturerName = manufacturerName;
        this.warranty = warranty;
        this.conditions = conditions;
        this.weight = weight;
        this.color = color;
        this.thumbnail = thumbnail;
        this.price = price;
        this.finalPrice = finalPrice;
        this.discountPercent = discountPercent;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
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

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getImages() {
        return images != null ? images : Collections.emptyList();
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ReviewCommentDto> getReviews() {
        return reviews != null ? reviews : Collections.emptyList();
    }

    public void setReviews(List<ReviewCommentDto> reviews) {
        this.reviews = reviews;
    }

}
