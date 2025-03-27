package vn.graybee.response.admin.directories.subcate;

import vn.graybee.models.directories.SubCategory;
import vn.graybee.response.BaseResponse;
import vn.graybee.response.admin.directories.tag.TagResponse;

import java.util.List;

public class SubCategoryResponse extends BaseResponse {

    private int id;

    private String name;

    private String status;

    private List<TagResponse> tags;

    public SubCategoryResponse(SubCategory subCategory, List<TagResponse> tags) {
        super(subCategory.getCreatedAt(), subCategory.getUpdatedAt());
        this.id = subCategory.getId();
        this.name = subCategory.getName();
        this.status = subCategory.getStatus();
        this.tags = tags;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
