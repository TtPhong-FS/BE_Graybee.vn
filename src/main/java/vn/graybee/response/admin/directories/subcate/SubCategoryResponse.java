package vn.graybee.response.admin.directories.subcate;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.models.directories.SubCategory;
import vn.graybee.response.admin.directories.tag.TagResponse;
import vn.graybee.response.admin.products.StatusResponse;

import java.time.LocalDateTime;
import java.util.List;

public class SubCategoryResponse {

    private int id;

    private String name;

    private StatusResponse status;

    private List<TagResponse> tags;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public SubCategoryResponse(SubCategory subCategory, List<TagResponse> tags) {
        this.id = subCategory.getId();
        this.name = subCategory.getName();
        this.status = new StatusResponse(subCategory.getStatus().getCode(), subCategory.getStatus().getDisplayName());
        this.tags = tags;
        this.createdAt = subCategory.getCreatedAt();
        this.updatedAt = subCategory.getUpdatedAt();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<TagResponse> getTags() {
        return tags;
    }

    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

}
