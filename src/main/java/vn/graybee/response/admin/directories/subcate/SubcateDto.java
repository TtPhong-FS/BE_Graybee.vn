package vn.graybee.response.admin.directories.subcate;

public class SubcateDto {

    private int id;

    private String subcategoryName;

    public SubcateDto() {
    }

    public SubcateDto(int id, String subcategoryName) {
        this.id = id;
        this.subcategoryName = subcategoryName;
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

}
