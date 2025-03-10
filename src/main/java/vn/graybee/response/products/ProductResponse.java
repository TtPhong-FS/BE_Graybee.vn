package vn.graybee.response.products;

import vn.graybee.response.BaseResponse;

import java.time.LocalDateTime;

public class ProductResponse extends BaseResponse {

    private long id;

    private int categoryId;

    private int manufacturerId;

    private String name;

    private int warranty;

    private float weight;

    private String dimension;

    private float price;

    private int discountPercentage;

    private float newPrice;

    private String color;

    private String description;

    private String thumbnail;

    private String conditions;

    public ProductResponse(LocalDateTime createdAt, LocalDateTime updatedAt, long id, int categoryId, int manufacturerId, String name, int warranty, float weight, String dimension, float price, int discountPercentage, float newPrice, String color, String description, String thumbnail, String conditions) {
        super(createdAt, updatedAt);
        this.id = id;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
        this.name = name;
        this.warranty = warranty;
        this.weight = weight;
        this.dimension = dimension;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.newPrice = newPrice;
        this.color = color;
        this.description = description;
        this.thumbnail = thumbnail;
        this.conditions = conditions;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

}
