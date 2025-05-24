package vn.graybee.taxonomy.category.dto.response;

public class CategorySubcategoryBasicDto {

    private int categoryId;

    private int subCategoryId;

    private String subcategoryName;

    public CategorySubcategoryBasicDto() {
    }

    public CategorySubcategoryBasicDto(int categoryId, int subCategoryId, String subcategoryName) {
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.subcategoryName = subcategoryName;
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

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

}
