package vn.graybee.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Document(indexName = "products")
public class ProductDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String name;

    private BigDecimal price;

    @Column(name = "final_price")
    private BigDecimal finalPrice;

    @Field(type = FieldType.Text)
    private String thumbnail;

    public ProductDocument(Long id, String name, BigDecimal price, BigDecimal finalPrice, String thumbnail) {
        this.id = id;
        this.name = name;

        this.price = price;
        this.finalPrice = finalPrice;

        this.thumbnail = thumbnail;
    }

    public ProductDocument() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
