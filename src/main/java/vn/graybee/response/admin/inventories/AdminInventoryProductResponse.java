package vn.graybee.response.admin.inventories;

public class AdminInventoryProductResponse {

    private long id;

    private String thumbnail;

    private String productName;

    private String productCode;

    public AdminInventoryProductResponse() {
    }

    public AdminInventoryProductResponse(long id, String thumbnail, String productName, String productCode) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.productName = productName;
        this.productCode = productCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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
