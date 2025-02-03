package vn.graybee.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public class ProductCreateRequest {

    @JsonProperty("category_id")
    @Positive(message = "Must be a positive number")
    @NotNull(message = "Cannot be null")
    private long categoryId;

    @JsonProperty("manufacturer_id")
    @Positive(message = "Must be a positive number")
    @NotNull(message = "Cannot be null")
    private long manufacturerId;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String model;

    @JsonProperty("product_name")
    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 300, message = "Must be between 1 and 300 characters")
    private String productName;

    @Pattern(regexp = "^(new|old)$", message = "Condition must match 'new' or 'old' ")
    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 5, message = "Must be 'new' or 'old' ")
    private String conditions;

    @Positive(message = "Must be a positive number")
    @NotNull(message = "Cannot be null")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int warranty;

    @PositiveOrZero(message = "Cannot be a negative number")
    @DecimalMax(value = "100.0", message = "Must be between 0 and 100kg")
    private float weight = 0;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String dimension = "unknown";

    @PositiveOrZero(message = "Cannot be a negative number")
    @DecimalMax(value = "100000000.0", message = "Must be between 0 and 100.000.000VND")
    @DecimalMin(value = "0.0", message = "Cannot be a negative number")
    private float price = 0;

    @Size(min = 1, max = 20, message = "Must be between 1 and 20 characters")
    private String color = "unknown";

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    private String thumbnail = "unknown";

    private String description = "unknown";

    @Size(min = 0, max = 20, message = "Must be between 0 and 20 characters")
    @JsonProperty("is_delete")
    private String isDelete = "false";

    private DetailDtoRequest detail;

    public DetailDtoRequest getDetail() {
        return detail;
    }

    public void setDetail(DetailDtoRequest detail) {
        this.detail = detail;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

}
