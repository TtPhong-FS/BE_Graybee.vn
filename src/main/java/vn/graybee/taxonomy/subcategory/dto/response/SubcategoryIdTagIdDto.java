package vn.graybee.taxonomy.subcategory.dto.response;

public class SubcategoryIdTagIdDto {

    private int subcategoryId;

    private int tagId;

    public SubcategoryIdTagIdDto() {
    }

    public SubcategoryIdTagIdDto(int subcategoryId, int tagId) {
        this.subcategoryId = subcategoryId;
        this.tagId = tagId;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

}
