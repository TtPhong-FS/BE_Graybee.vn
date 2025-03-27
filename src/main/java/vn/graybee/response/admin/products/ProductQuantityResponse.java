package vn.graybee.response.admin.products;

public class ProductQuantityResponse {

    private long id;

    private int quantity;

    public ProductQuantityResponse() {
    }

    public ProductQuantityResponse(long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
