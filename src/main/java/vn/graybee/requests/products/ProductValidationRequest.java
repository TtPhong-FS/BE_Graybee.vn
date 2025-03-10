package vn.graybee.requests.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductValidationRequest {

    @JsonProperty("category_name")
    @NotBlank(message = "Tên danh mục không thể trống")
    private String categoryName;

    @JsonProperty("manufacturer_name")
    @NotBlank(message = "Tên nhà sản xuất không thể trống")
    private String manufacturerName;

    @NotBlank(message = "Tên sản phẩm không thể trống")
    @Size(min = 5, max = 300, message = "Độ dài ít nhất từ 5 đến 300 ký tự")
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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
