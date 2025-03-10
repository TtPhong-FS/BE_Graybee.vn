package vn.graybee.models.products;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import vn.graybee.enums.GeneralStatus;
import vn.graybee.models.others.BaseModel;

@Entity
@Table(name = "products")
public class Product extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "manufacturer_id")
    private Integer manufacturerId;

    @Column(unique = true, nullable = false, length = 200)
    private String name;

    @Column(name = "conditions", length = 5, nullable = false)
    private String conditions;

    @Column(name = "warranty", nullable = false)
    private int warranty;

    private float weight;

    @Column(name = "dimension", length = 50)
    private String dimension;

    private float price;

    private int discount_percent;

    private float newPrice;

    @Column(length = 35)
    private String color;

    @Column(name = "description")
    private String description;

    @Column(length = 300)
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private GeneralStatus status;

    public Product() {
    }

    public Product(String name, int warranty, float weight, String dimension, float price, int discount_percent, String color, String description, String thumbnail) {
        this.name = name;
        this.warranty = warranty;
        this.weight = weight;
        this.dimension = dimension;
        this.price = price;
        this.discount_percent = discount_percent;
        this.color = color;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(int discount_percent) {
        this.discount_percent = discount_percent;
    }

    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
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

    public GeneralStatus getStatus() {
        return status;
    }

    public void setStatus(GeneralStatus status) {
        this.status = status;
    }

}
