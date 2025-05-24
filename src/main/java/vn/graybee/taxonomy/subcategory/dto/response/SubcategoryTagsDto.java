package vn.graybee.taxonomy.subcategory.dto.response;

import vn.graybee.taxonomy.tag.dto.response.TagDto;

import java.util.Collections;
import java.util.List;

public class SubcategoryTagsDto {

    private int id;

    private String name;

    private List<TagDto> tags;

    public SubcategoryTagsDto() {
    }

    public SubcategoryTagsDto(int id, String name) {
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

    public List<TagDto> getTags() {
        return tags != null ? tags : Collections.emptyList();
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

}
