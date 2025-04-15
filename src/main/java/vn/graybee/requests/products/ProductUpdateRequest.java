package vn.graybee.requests.products;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.enums.ProductStatus;
import vn.graybee.exceptions.BusinessCustomException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ProductUpdateRequest {

    private List<Integer> tags;

    private List<Integer> subcategories;

    @NotBlank(message = "Tên sản phẩm không thể trống")
    @Size(min = 5, max = 300, message = "Độ dài ít nhất từ 5 đến 300 ký tự")
    private String name;

    @NotNull(message = "Chọn nhà sản xuất")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private int manufacturerId;

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

    @NotNull(message = "Giá không thể trống")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    @DecimalMax(value = "100000000.0", message = "Giá tối đa là 100.000.000VND")
    @DecimalMin(value = "0.0", message = "Giá thấp nhất là 0VND")
    private BigDecimal price;

    @NotNull(message = "Giảm giá không thể trống")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private int discountPercent;

    @Size(max = 35, message = "Độ dài tối đa 35 ký tự")
    private String color;

    private boolean inStock;

    private boolean hasPromotion;

    @NotNull(message = "Số lượng không thể trống")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private int quantity;

    @NotNull(message = "Trạng thái không được để trống")
    private String status;

    private String description;

    private List<String> images;

    public ProductStatus getStatusEnum() {
        try {
            return ProductStatus.valueOf(status.toUpperCase());
        } catch (RuntimeException e) {
            throw new BusinessCustomException(ConstantGeneral.status, ConstantGeneral.status_invalid + status);
        }
    }

    public List<Integer> getSubcategories() {
        return subcategories != null ? subcategories : Collections.emptyList();
    }

    public void setSubcategories(List<Integer> subcategories) {
        this.subcategories = subcategories;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public List<Integer> getTags() {
        return tags != null ? tags : Collections.emptyList();
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasPromotion() {
        return hasPromotion;
    }

    public void setHasPromotion(boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images != null ? images : Collections.emptyList();
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
