package vn.graybee.requests.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class SubCategoryCreateRequest {

    @NotBlank(message = "Không được để trống")
    @Size(max = 100, message = "Độ dài không được vượt quá 100 ký tự")
    private String name;

    private List<String> subcategoryDetails;

    public List<String> getSubcategoryDetails() {
        return subcategoryDetails;
    }

    public void setSubcategoryDetails(List<String> subcategoryDetails) {
        this.subcategoryDetails = subcategoryDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
