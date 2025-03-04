package vn.graybee.requests.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.List;

public class CategoryCreateRequest {

    @NotBlank(message = "Không được để trống")
    @Size(max = 50, message = "Độ dài không được vượt quá 50 ký tự")
    private String name;

    private List<String> subCategories;

    public List<String> getSubCategories() {
        return subCategories != null ? subCategories : Collections.emptyList();
    }

    public void setSubCategories(List<String> subCategories) {
        this.subCategories = subCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
