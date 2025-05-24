package vn.graybee.taxonomy.subcategory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.List;

public class SubcategoryCreateRequest {

    @NotBlank(message = "Không được để trống")
    @Size(max = 35, message = "Độ dài không được vượt quá 35 ký tự")
    private String name;

    private List<Integer> tags;

    public List<Integer> getTags() {
        return tags != null ? tags : Collections.emptyList();
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
