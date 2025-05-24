package vn.graybee.taxonomy.subcategory.dto.response;

public class SubcategoryTagDto {

    private int id;

    private int tagId;

    private String tagName;

    public SubcategoryTagDto() {
    }

    public SubcategoryTagDto(int id, int tagId, String tagName) {
        this.id = id;
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}
