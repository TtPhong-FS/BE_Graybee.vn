package vn.graybee.response;

public class ProductIdAndTypeResponse {

    private long id;

    private String product_type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public ProductIdAndTypeResponse(long id, String product_type) {
        this.id = id;
        this.product_type = product_type;
    }

}
