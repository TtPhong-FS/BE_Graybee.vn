package vn.graybee.response.admin.directories.subcate;

import vn.graybee.models.categories.SubCategory;
import vn.graybee.response.BaseResponse;
import vn.graybee.response.admin.directories.tag.TagResponse;

import java.util.List;

public class SubCategoryResponse extends BaseResponse {

    private int id;

    private String subcategoryName;

    private String status;

    private List<TagResponse> tags;

    public SubCategoryResponse(SubCategory subCategory, List<TagResponse> tags) {
        super(subCategory.getCreatedAt(), subCategory.getUpdatedAt());
        this.id = subCategory.getId();
        this.subcategoryName = subCategory.getSubcategoryName();
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

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
