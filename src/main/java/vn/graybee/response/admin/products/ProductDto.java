package vn.graybee.response.admin.products;

import vn.graybee.models.products.Product;
import vn.graybee.response.BaseResponse;
import vn.graybee.response.admin.directories.tag.TagResponse;

import java.util.List;

public class ProductDto extends BaseResponse {

    private long id;

    private int categoryId;

    private int manufacturerId;

    private List<TagResponse> tags;

    private String code;

    private String name;

    private int warranty;

    private float weight;

    private String dimension;

    private double price;

    private int discountPercent;

    private double finalPrice;

    private String color;

    private String thumbnail;

    private List<String> images;

    private String conditions;

    private String description;

    private boolean inStock;

    private int quantity;

    private String status;

    public ProductDto(Product product, int categoryId, int manufacturerId, List<TagResponse> tags, List<String> images, int quantity, String description) {
        super(product.getCreatedAt(), product.getUpdatedAt());
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
        this.tags = tags;
        this.warranty = product.getWarranty();
        this.weight = product.getWeight();
        this.dimension = product.getDimension();
        this.price = product.getPrice();
        this.discountPercent = product.getDiscountPercent();
        this.finalPrice = product.getFinalPrice();
        this.color = product.getColor();
        this.images = images;
        this.thumbnail = product.getThumbnail();
        this.conditions = product.getConditions();
        this.inStock = product.isStock();
        this.quantity = quantity;
        this.description = description;
        this.status = product.getStatus();
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public List<TagResponse> getTags() {
        return tags;
    }

    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
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

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
