package vn.graybee.response.admin.products;

import vn.graybee.models.products.Product;
import vn.graybee.response.BaseResponse;
import vn.graybee.response.admin.directories.tag.TagResponse;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDto extends BaseResponse {

    private long id;

    private String categoryName;

    private String manufacturerName;

    private List<TagResponse> tags;

    private String productCode;

    private String productName;

    private int warranty;

    private float weight;

    private String dimension;

    private double price;

    private int discountPercent;

    private double finalPrice;

    private String color;

    private String thumbnail;

    private String conditions;

    private String description;

    private boolean inStock;

    private int quantity;

    private String status;

    public ProductDto(LocalDateTime createdAt, LocalDateTime updatedAt, Product product, String categoryName, String manufacturerName, List<TagResponse> tags, int quantity, String description) {
        super(createdAt, updatedAt);
        this.id = product.getId();
        this.productCode = product.getProductCode();
        this.productName = product.getProductName();
        this.categoryName = categoryName;
        this.manufacturerName = manufacturerName;
        this.tags = tags;
        this.warranty = product.getWarranty();
        this.weight = product.getWeight();
        this.dimension = product.getDimension();
        this.price = product.getPrice();
        this.discountPercent = product.getDiscountPercent();
        this.finalPrice = product.getFinalPrice();
        this.color = product.getColor();
        this.thumbnail = product.getThumbnail();
        this.conditions = product.getConditions();
        this.inStock = product.isStock();
        this.quantity = quantity;
        this.description = description;
        this.status = product.getStatus();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public List<TagResponse> getTags() {
        return tags;
    }

    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
