package vn.graybee.modules.product.dto.response;

import vn.graybee.modules.product.enums.ProductStatus;

public class InventoryProductDto {

    private Long productId;

    private String productName;

    private ProductStatus productStatus;

    public InventoryProductDto() {
    }

    public InventoryProductDto(Long productId, String productName, ProductStatus productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.productStatus = productStatus;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
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

}
