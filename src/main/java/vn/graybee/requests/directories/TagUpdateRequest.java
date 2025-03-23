package vn.graybee.requests.directories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TagUpdateRequest {

    @NotBlank(message = "Không được để trống")
    @Size(max = 100, message = "Độ dài không được vượt quá 100 ký tự")
    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}
