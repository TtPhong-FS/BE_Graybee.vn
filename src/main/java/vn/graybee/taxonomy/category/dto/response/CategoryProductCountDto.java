package vn.graybee.taxonomy.category.dto.response;

public class CategoryProductCountDto {

    private int id;

    private String name;

    private int productCount;

    public CategoryProductCountDto() {
    }

    public CategoryProductCountDto(int id, String name, int productCount) {
        this.id = id;
        this.name = name;
        this.productCount = productCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

}
