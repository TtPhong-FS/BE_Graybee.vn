package vn.graybee.response.admin.directories.manufacturer;

public class ManufacturerProductCountResponse {

    private int id;

    private int productCount;

    public ManufacturerProductCountResponse(int id, int productCount) {
        this.id = id;
        this.productCount = productCount;
    }

    public ManufacturerProductCountResponse() {
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
