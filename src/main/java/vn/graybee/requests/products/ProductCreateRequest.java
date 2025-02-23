package vn.graybee.requests.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.enums.Condition;
import vn.graybee.requests.DetailDtoRequest;

@MappedSuperclass
public class ProductCreateRequest {

    @JsonProperty("category_name")
    @NotBlank(message = "Cannot be blank")
    private String categoryName;

    @JsonProperty("manufacturer_name")
    @NotBlank(message = "Cannot be blank")
    private String manufacturerName;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String model;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 300, message = "Must be between 1 and 300 characters")
    private String name;

    @NotNull(message = "Cannot be null")
    private Condition conditions;

    @Positive(message = "Must be a positive number")
    @NotNull(message = "Cannot be null")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int warranty;

    @PositiveOrZero(message = "Cannot be a negative number")
    @DecimalMax(value = "100.0", message = "Must be between 0 and 100kg")
    private float weight;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String dimension;

    @PositiveOrZero(message = "Cannot be a negative number")
    @DecimalMax(value = "100000000.0", message = "Must be between 0 and 100.000.000VND")
    @DecimalMin(value = "0.0", message = "Cannot be a negative number")
    private float price;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int discount_percent;

    @PositiveOrZero(message = "Cannot be a negative number")
    @DecimalMax(value = "100000000.0", message = "Must be between 0 and 100.000.000VND")
    @DecimalMin(value = "0.0", message = "Cannot be a negative number")
    private float newPrice;

    @Size(min = 1, max = 35, message = "Must be between 1 and 35 characters")
    private String color;

    @Size(min = 1, max = 300, message = "Must be between 1 and 300 characters")
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

    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Condition getConditions() {
        return conditions;
    }

    public void setConditions(Condition conditions) {
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
