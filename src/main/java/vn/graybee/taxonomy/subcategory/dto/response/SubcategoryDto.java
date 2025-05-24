package vn.graybee.taxonomy.subcategory.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import vn.graybee.response.admin.products.StatusResponse;
import vn.graybee.taxonomy.subcategory.model.Subcategory;
import vn.graybee.taxonomy.tag.dto.response.TagDto;

import java.time.LocalDateTime;
import java.util.List;

public class SubcategoryDto {

    private int id;

    private String name;

    private StatusResponse status;

    private List<TagDto> tags;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public SubcategoryDto(Subcategory subCategory) {
        this.id = subCategory.getId();
        this.name = subCategory.getName();
        this.status = new StatusResponse(subCategory.getStatus().getCode(), subCategory.getStatus().getDisplayName());
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

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
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
