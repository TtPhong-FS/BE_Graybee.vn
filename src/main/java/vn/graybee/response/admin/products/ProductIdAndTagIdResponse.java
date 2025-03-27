package vn.graybee.response.admin.products;

public class ProductIdAndTagIdResponse {

    private long productId;

    private int tagId;


    public ProductIdAndTagIdResponse(long productId, int tagId) {
        this.productId = productId;
        this.tagId = tagId;
    }

    public ProductIdAndTagIdResponse() {
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

}
