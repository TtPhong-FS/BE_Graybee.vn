package vn.graybee.response.admin.directories.subcate;

public class SubcategoryTagIdResponse {

    private int subcategoryId;

    private int tagId;

    public SubcategoryTagIdResponse() {
    }

    public SubcategoryTagIdResponse(int subcategoryId, int tagId) {
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
