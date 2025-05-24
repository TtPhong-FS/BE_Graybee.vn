package vn.graybee.taxonomy.category.dto.response;

public class CategoryIdSubcategoryIdDto {

    private int categoryId;

    private int subCategoryId;

    public CategoryIdSubcategoryIdDto() {
    }

    public CategoryIdSubcategoryIdDto(int categoryId, int subCategoryId) {
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

}
