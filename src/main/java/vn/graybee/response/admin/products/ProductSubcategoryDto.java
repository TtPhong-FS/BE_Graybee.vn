package vn.graybee.response.admin.products;

public class ProductSubcategoryDto {

    private long productId;

    private int id;

    private String name;

    public ProductSubcategoryDto() {
    }

    public ProductSubcategoryDto(long productId, int id, String name) {
        this.productId = productId;
        this.id = id;
        this.name = name;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
