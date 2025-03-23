package vn.graybee.requests.products;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.List;

@MappedSuperclass
public class ProductCreateRequest {

    @Size(max = 35, message = "Độ dài tối đa 35 ký tự")
    @NotBlank(message = "Tên danh mục không thể trống")
    private String categoryName;

    @Size(max = 50, message = "Độ dài tối đa 50 ký tự")
    @NotBlank(message = "Tên nhà sản xuất không thể trống")
    private String manufacturerName;

    private List<Integer> tags;

    @NotBlank(message = "Tên sản phẩm không thể trống")
    @Size(min = 5, max = 300, message = "Độ dài ít nhất từ 5 đến 300 ký tự")
    private String productName;

    @NotNull(message = "Tình trạng không thể trống")
    private String conditions;

    @NotNull(message = "Bảo hành không thể trống")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private int warranty;

    @PositiveOrZero(message = "Vui lòng nhập số dương")
    @DecimalMax(value = "100.0", message = "Đã vượt quá cân nặng tối đa là 100kg")
    private float weight;

    @Size(max = 50, message = "Độ dài không vượt quá 50 ký tự")
    private String dimension;

    @PositiveOrZero(message = "Vui lòng nhập số dương")
    @DecimalMax(value = "100000000.0", message = "Giá tối đa là 100.000.000VND")
    @DecimalMin(value = "0.0", message = "Giá thấp nhất là 0VND")
    private double price;

    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private int discountPercent;

    @Size(max = 35, message = "Độ dài tối đa 35 ký tự")
    private String color;

    private boolean inStock;

    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private int quantity;

    @Size(max = 30, message = "Độ dài tối đa 35 ký tự")
    private String status;

    private String description;

    private List<String> images;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Integer> getTags() {
        return tags != null ? tags : Collections.emptyList();
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
