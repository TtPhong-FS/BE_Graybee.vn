package vn.graybee.requests.product;

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
import org.springframework.web.multipart.MultipartFile;

@MappedSuperclass
public class ProductCreateRequest {

    private MultipartFile file;

    @JsonProperty("category_id")
    @Positive(message = "Category Id must be a positive number")
    @NotNull(message = "Product's Category id Cannot be null")
    private long categoryId;

    @JsonProperty("manufacturer_id")
    @Positive(message = "Manufacturer Id must be a positive number")
    @NotNull(message = "Product's Manufacturer id Cannot be null")
    private long manufacturerId;

    @JsonProperty("product_type")
    @Size(min = 1, max = 50, message = "Type can only be between 1 and 50 characters")
    @NotNull(message = "Product's type cannot be null")
    private String productType;

    @NotEmpty(message = "Product's model cannot be empty")
    @Size(min = 1, max = 100, message = "Model must be between 5 and 100 characters")
    private String model;

    @NotEmpty(message = "Product's name cannot be empty")
    @Size(min = 1, max = 300, message = "Name must be between 5 and 300 characters")
    private String name;

    @Pattern(regexp = "^(new|old)$", message = "Condition must match 'new' or 'old' ")
    @NotEmpty(message = "Product's condition cannot be empty")
    @Size(min = 1, max = 5, message = "Condition must be 'new' or 'old' ")
    private String conditions;

    @NotNull(message = "Product's warranty cannot be null")
    @PositiveOrZero(message = "Warranty must be greater than to 0")
    private int warranty;

    @PositiveOrZero(message = "Price cannot be negative")
    @DecimalMax(value = "100.0", message = "Weight must be less than or equal to 100")
    private float weight = 0;

    @Size(min = 1, max = 50, message = "Dimension must be greater than 10 or equal to 50")
    private String dimension = "unknown";

    @PositiveOrZero(message = "Price cannot be negative")
    @DecimalMax(value = "100000000.0", message = "Price must be less than or equal to 100.000.000")
    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0")
    private float price = 0;

    @Size(min = 1, max = 20, message = "Color must be between 0 and 20 characters")
    private String color = "unknown";

    @Size(min = 1, max = 200, message = "Thumbnail must be between 0 and 200 characters")
    private String thumbnail = "unknown";

    private String description = "unknown";

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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
