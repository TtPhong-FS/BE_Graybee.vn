package vn.graybee.response.admin.products;

public class ProductTagDto {

    private Long productId;

    private int id;

    private String tagName;

    public ProductTagDto() {
    }

    public ProductTagDto(Long productId, int id, String tagName) {
        this.productId = productId;
        this.id = id;
        this.tagName = tagName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
