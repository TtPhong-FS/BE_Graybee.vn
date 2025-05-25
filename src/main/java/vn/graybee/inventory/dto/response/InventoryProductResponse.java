package vn.graybee.inventory.dto.response;

public class InventoryProductResponse {

    private Long productId;

    private String productName;

    private String productCode;

    public InventoryProductResponse() {
    }

    public InventoryProductResponse(Long productId, String productName, String productCode) {
        this.productId = productId;
        this.productName = productName;
        this.productCode = productCode;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

}
