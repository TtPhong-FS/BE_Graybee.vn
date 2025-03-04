package vn.graybee.requests.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductValidationRequest {

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

}
