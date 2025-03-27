package vn.graybee.response.admin.directories.category;

public class CategoryProductCountResponse {

    private int id;

    private int productCount;

    public CategoryProductCountResponse() {
    }

    public CategoryProductCountResponse(int id, int productCount) {
        this.id = id;
        this.productCount = productCount;
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
