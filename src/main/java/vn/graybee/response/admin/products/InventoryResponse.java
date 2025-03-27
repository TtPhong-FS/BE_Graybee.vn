package vn.graybee.response.admin.products;

import vn.graybee.models.products.Inventory;
import vn.graybee.response.BaseResponse;

public class InventoryResponse extends BaseResponse {

    private int id;

    private String productCode;

    private int quantity;

    private String status;

    public InventoryResponse(Inventory inventory) {
        super(inventory.getCreatedAt(), inventory.getUpdatedAt());
        this.id = inventory.getId();
        this.productCode = inventory.getProductCode();
        this.quantity = inventory.getQuantity();
        this.status = inventory.getStatus();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
