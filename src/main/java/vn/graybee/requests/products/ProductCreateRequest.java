package vn.graybee.requests.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public class ProductCreateRequest {

    @JsonProperty("category_id")
    @Positive(message = "Category Id must be a positive number")
    @NotNull(message = "Product's Category id Cannot be null")
    private long categoryId;

    @JsonProperty("manufacturer_id")
    @Positive(message = "Manufacturer Id must be a positive number")
    @NotNull(message = "Product's Manufacturer id Cannot be null")
    private long manufacturerId;

    @NotEmpty(message = "Product's name cannot be empty")
    @Size(min = 5, max = 300, message = "Name must be between 5 and 300 characters")
    private String name;

    @Pattern(regexp = "^(new|old)$", message = "Condition must match 'new' or 'old' ")
    @NotEmpty(message = "Product's condition cannot be empty")
    @Size(min = 3, max = 5, message = "Condition must be 'New' or 'Old' ")
    private String conditions;

    @NotNull(message = "Product's warranty cannot be null")
    @PositiveOrZero(message = "Warranty must be greater than to 0")
    private int warranty;

    @PositiveOrZero(message = "Price cannot be negative")
    @DecimalMax(value = "100000000.0", message = "Price must be less than or equal to 100.000.000")
    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0")
    @NotNull(message = "Product's price cannot be null")
    private Float price;

    @Size(min = 0, max = 20, message = "Color must be between 0 and 20 characters")
    private String color;

    @Size(min = 0, max = 200, message = "Thumbnail must be between 0 and 200 characters")
    private String thumbnail;

    private String description;

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
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
