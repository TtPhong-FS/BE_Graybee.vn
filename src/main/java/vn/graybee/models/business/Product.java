package vn.graybee.models.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import vn.graybee.models.others.BaseModel;

@Entity
@Table(name = "products")
public class Product extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Column(nullable = false, length = 100, unique = true)
    private String model;

    @Column(name = "product_type", nullable = false, length = 30)
    private String productType;

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

    @Column(length = 20)
    private String color;

    @Column(name = "description")
    private String description;

    @Column(length = 300)
    private String thumbnail;

    @Column(name = "is_delete", length = 20)
    private String isDelete;

    public Product() {
    }

    public Product(String model, String productType, String name, String conditions, int warranty, float weight, String dimension, float price, String color, String description, String thumbnail, String isDelete) {
        this.model = model;
        this.productType = productType;
        this.name = name;
        this.conditions = conditions;
        this.warranty = warranty;
        this.weight = weight;
        this.dimension = dimension;
        this.price = price;
        this.color = color;
        this.description = description;
        this.thumbnail = thumbnail;
        this.isDelete = isDelete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

}
