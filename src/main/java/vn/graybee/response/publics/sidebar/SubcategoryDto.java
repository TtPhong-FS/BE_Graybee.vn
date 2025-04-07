package vn.graybee.response.publics.sidebar;

import vn.graybee.response.admin.directories.tag.TagResponse;

import java.util.Collections;
import java.util.List;

public class SubcategoryDto {

    private int id;

    private String name;

    private List<TagResponse> tags;

    public SubcategoryDto() {
    }

    public SubcategoryDto(int id, String name) {
        this.id = id;
        this.name = name;
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

    public List<TagResponse> getTags() {
        return tags != null ? tags : Collections.emptyList();
    }

    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
    }

}
