package vn.graybee.response.admin.products;

public class ProductSubcategoryIDResponse {

    private long productId;

    private int subcategoryId;

    public ProductSubcategoryIDResponse() {
    }

    public ProductSubcategoryIDResponse(long productId, int subcategoryId) {
        this.productId = productId;
        this.subcategoryId = subcategoryId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

}
