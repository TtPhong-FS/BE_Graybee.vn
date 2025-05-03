package vn.graybee.response.admin.directories.category;

public class CategoryProductCountResponse {

    private int id;

    private String name;

    private int productCount;

    public CategoryProductCountResponse() {
    }

    public CategoryProductCountResponse(int id, String name, int productCount) {
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
