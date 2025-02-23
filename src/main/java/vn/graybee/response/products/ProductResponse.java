package vn.graybee.response.products;

import vn.graybee.models.products.Product;
import vn.graybee.response.DetailDtoResponse;

public class ProductResponse {

    private long id;

    private String model;

    private String name;

    private int warranty;

    private float weight;

    private String dimension;

    private float price;

    private String color;

    private String description;

    private String thumbnail;

    private String conditions;

    private int categoryId;

    private int manufacturerId;

    private DetailDtoResponse detail;

    public ProductResponse() {
    }

    public ProductResponse(Product product, DetailDtoResponse detail) {
        this.id = product.getId();
        this.model = product.getModel();
        this.name = product.getName();
        this.warranty = product.getWarranty();
        this.weight = product.getWeight();
        this.dimension = product.getDimension();
        this.price = product.getPrice();
        this.color = product.getColor();
        this.description = product.getDescription();
        this.thumbnail = product.getThumbnail();
        this.conditions = product.getConditions();
        this.categoryId = product.getCategory().getId();
        this.manufacturerId = product.getManufacturer().getId();
        this.detail = detail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWarranty() {
        return warranty;
    }

    public void setWarranty(Integer warranty) {
        this.warranty = warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public DetailDtoResponse getDetail() {
        return detail;
    }

    public void setDetail(DetailDtoResponse detail) {
        this.detail = detail;
    }

}
