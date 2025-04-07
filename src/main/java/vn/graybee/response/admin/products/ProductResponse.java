package vn.graybee.response.admin.products;

import vn.graybee.models.products.Product;
import vn.graybee.response.BaseResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponse extends BaseResponse {

    private long id;

    private String categoryName;

    private String manufacturerName;

    private String code;

    private String name;

    private int warranty;

    private float weight;

    private String dimension;

    private BigDecimal price;

    private int discountPercent;

    private BigDecimal finalPrice;

    private String color;

    private String thumbnail;

    private String conditions;

    private boolean inStock;

    private int quantity;

    private String status;

    public ProductResponse(LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
    }

    public ProductResponse(Product product, String categoryName, String manufacturerName, int quantity) {
        super(product.getCreatedAt(), product.getUpdatedAt());
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.categoryName = categoryName;
        this.manufacturerName = manufacturerName;
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
        this.status = product.getStatus();
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

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

}
