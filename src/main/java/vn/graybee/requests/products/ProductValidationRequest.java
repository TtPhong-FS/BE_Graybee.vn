package vn.graybee.requests.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductValidationRequest {

    @Size(min = 2, max = 35, message = "Độ dài ít nhất từ 2 đến 35 ký tự")
    @NotBlank(message = "Tên danh mục không thể trống")
    private String categoryName;

    @Size(min = 2, max = 50, message = "Độ dài ít nhất từ 2 đến 50 ký tự")
    @NotBlank(message = "Tên nhà sản xuất không thể trống")
    private String manufacturerName;

    @NotBlank(message = "Tên sản phẩm không thể trống")
    @Size(min = 5, max = 300, message = "Độ dài ít nhất từ 5 đến 300 ký tự")
    private String productName;

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

}
