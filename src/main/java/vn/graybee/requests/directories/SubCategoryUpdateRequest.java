package vn.graybee.requests.directories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import vn.graybee.enums.CategoryStatus;

import java.util.Collections;
import java.util.List;

public class SubCategoryUpdateRequest {

    @NotBlank(message = "Không được để trống")
    @Size(max = 35, message = "Độ dài không được vượt quá 35 ký tự")
    private String subcategoryName;

    private CategoryStatus status;

    private List<Integer> tags;

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

    public List<Integer> getTags() {
        return tags != null ? tags : Collections.emptyList();
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

}
