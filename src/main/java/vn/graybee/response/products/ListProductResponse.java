//package vn.graybee.response.products;
//
//import vn.graybee.models.products.Product;
//import vn.graybee.response.BaseResponse;
//
//import java.time.LocalDateTime;
//
//public class ListProductResponse extends BaseResponse {
//
//    private long id;
//
//    private String categoryName;
//
//    private String manufacturerName;
//
//    private String productCode;
//
//    private String productName;
//
//    private int warranty;
//
//    private float weight;
//
//    private String dimension;
//
//    private float price;
//
//    private int discountPercent;
//
//    private float finalPrice;
//
//    private String color;
//
//    private String thumbnail;
//
//    private String conditions;
//
//    private String status;
//
//    public ListProductResponse(LocalDateTime createdAt, LocalDateTime updatedAt, Product product, String categoryName, String manufacturerName) {
//        super(createdAt, updatedAt);
//        this.id = product.getId();
//        this.productCode = product.getProductCode();
//        this.productName = product.getProductName();
//        this.categoryName = categoryName;
//        this.manufacturerName = manufacturerName;
//        this.warranty = product.getWarranty();
//        this.weight = product.getWeight();
//        this.dimension = product.getDimension();
//        this.price = product.getPrice();
//        this.discountPercent = product.getDiscountPercent();
//        this.finalPrice = product.getFinalPrice();
//        this.color = product.getColor();
//        this.thumbnail = product.getThumbnail();
//        this.conditions = product.getConditions();
//        this.status = product.getStatus();
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }
//
//    public String getManufacturerName() {
//        return manufacturerName;
//    }
//
//    public void setManufacturerName(String manufacturerName) {
//        this.manufacturerName = manufacturerName;
//    }
//
//    public String getProductCode() {
//        return productCode;
//    }
//
//    public void setProductCode(String productCode) {
//        this.productCode = productCode;
//    }
//
//    public String getProductName() {
//        return productName;
//    }
//
//    public void setProductName(String productName) {
//        this.productName = productName;
//    }
//
//    public int getWarranty() {
//        return warranty;
//    }
//
//    public void setWarranty(int warranty) {
//        this.warranty = warranty;
//    }
//
//    public float getWeight() {
//        return weight;
//    }
//
//    public void setWeight(float weight) {
//        this.weight = weight;
//    }
//
//    public String getDimension() {
//        return dimension;
//    }
//
//    public void setDimension(String dimension) {
//        this.dimension = dimension;
//    }
//
//    public float getPrice() {
//        return price;
//    }
//
//    public void setPrice(float price) {
//        this.price = price;
//    }
//
//    public int getDiscountPercent() {
//        return discountPercent;
//    }
//
//    public void setDiscountPercent(int discountPercent) {
//        this.discountPercent = discountPercent;
//    }
//
//    public float getFinalPrice() {
//        return finalPrice;
//    }
//
//    public void setFinalPrice(float finalPrice) {
//        this.finalPrice = finalPrice;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public String getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(String thumbnail) {
//        this.thumbnail = thumbnail;
//    }
//
//    public String getConditions() {
//        return conditions;
//    }
//
//    public void setConditions(String conditions) {
//        this.conditions = conditions;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//}
