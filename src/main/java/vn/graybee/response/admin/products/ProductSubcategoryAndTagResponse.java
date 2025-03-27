package vn.graybee.response.admin.products;

import vn.graybee.response.admin.directories.subcate.SubcateDto;
import vn.graybee.response.admin.directories.tag.TagResponse;

import java.util.List;

public class ProductSubcategoryAndTagResponse {

    private long id;

    private String code;

    private List<SubcateDto> subcategories;

    private List<TagResponse> tags;

    public ProductSubcategoryAndTagResponse() {
    }

    public ProductSubcategoryAndTagResponse(long id, String code, List<SubcateDto> subcategories, List<TagResponse> tags) {
        this.id = id;
        this.code = code;
        this.subcategories = subcategories;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<SubcateDto> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcateDto> subcategories) {
        this.subcategories = subcategories;
    }

    public List<TagResponse> getTags() {
        return tags;
    }

    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
    }

}
