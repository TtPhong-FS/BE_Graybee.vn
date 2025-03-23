package vn.graybee.response.admin.directories.tag;

import vn.graybee.models.categories.Tag;

public class TagResponse {

    private int id;

    private String tagName;

    public TagResponse() {
    }

    public TagResponse(Tag tag) {
        this.id = tag.getId();
        this.tagName = tag.getTagName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}
