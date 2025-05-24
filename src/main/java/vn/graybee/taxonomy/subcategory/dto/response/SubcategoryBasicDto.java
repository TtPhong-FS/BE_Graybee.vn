package vn.graybee.taxonomy.subcategory.dto.response;

public class SubcategoryBasicDto {

    private int id;

    private String name;

    public SubcategoryBasicDto() {
    }

    public SubcategoryBasicDto(int id, String name) {
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

}
