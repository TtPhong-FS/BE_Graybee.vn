package vn.graybee.requests.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.DetailDtoRequest;

@MappedSuperclass
public class ProductCreateRequest {

    @JsonProperty("category_name")
    @NotBlank(message = "Tên danh mục không thể trống")
    private String categoryName;

    @JsonProperty("manufacturer_name")
    @NotBlank(message = "Tên nhà sản xuất không thể trống")
    private String manufacturerName;

    @NotBlank(message = "Tên sản phẩm không thể trống")
    @Size(min = 5, max = 300, message = "Độ dài ít nhất từ 5 đến 300 ký tự")
    private String name;

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
    private float price;

    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private int discount_percent;

    @Size(max = 35, message = "Độ dài tối đa 35 ký tự")
    private String color;

    @Size(max = 300, message = "Độ dài tối đa 300 ký tự")
    private String thumbnail;

    private String description;

    private DetailDtoRequest detail;

    public DetailDtoRequest getDetail() {
        return detail;
    }

    public void setDetail(DetailDtoRequest detail) {
        this.detail = detail;
    }

    public int getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(int discount_percent) {
        this.discount_percent = discount_percent;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
