package vn.graybee.requests.categories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.List;

public class SubCategoryCreateRequest {

    @NotBlank(message = "Không được để trống")
    @Size(max = 35, message = "Độ dài không được vượt quá 35 ký tự")
    private String name;

    private List<String> tags;

    public List<String> getTags() {
        return tags != null ? tags : Collections.emptyList();
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
