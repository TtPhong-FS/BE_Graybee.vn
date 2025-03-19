package vn.graybee.response.products;

public class ProductTagDto {

    private Long productId;

    private String tagName;

    public ProductTagDto() {
    }

    public ProductTagDto(Long productId, String tagName) {
        this.productId = productId;
        this.tagName = tagName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}
