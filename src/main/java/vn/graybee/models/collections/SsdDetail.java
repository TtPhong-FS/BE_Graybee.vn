package vn.graybee.models.collections;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import vn.graybee.models.products.Product;

@Entity
@Table(name = "ssd_details")
public class SsdDetail {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "communication_standard", length = 50, nullable = false)
    private String communicationStandard;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "life_span")
    private int lifeSpan;

    @Column(name = "reading_speed", nullable = false)
    private int readingSpeed;

    @Column(name = "writing_speed", nullable = false)
    private int writingSpeed;

    @Column(name = "storage_temperature", length = 50)
    private String storageTemperature;

    @Column(name = "operating_temperature", length = 50)
    private String operatingTemperature;

    @Column(name = "random_reading_speed", nullable = false)
    private int randomReadingSpeed;

    @Column(name = "random_writing_speed", nullable = false)
    private int randomWritingSpeed;

    @Column(name = "software", length = 100)
    private String software;

    public SsdDetail() {
    }

    public SsdDetail(Long productId, String communicationStandard, int capacity, int lifeSpan, int readingSpeed, int writingSpeed, String storageTemperature, String operatingTemperature, int randomReadingSpeed, int randomWritingSpeed, String software) {
        this.productId = productId;
        this.communicationStandard = communicationStandard;
        this.capacity = capacity;
        this.lifeSpan = lifeSpan;
        this.readingSpeed = readingSpeed;
        this.writingSpeed = writingSpeed;
        this.storageTemperature = storageTemperature;
        this.operatingTemperature = operatingTemperature;
        this.randomReadingSpeed = randomReadingSpeed;
        this.randomWritingSpeed = randomWritingSpeed;
        this.software = software;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getCommunicationStandard() {
        return communicationStandard;
    }

    public void setCommunicationStandard(String communicationStandard) {
        this.communicationStandard = communicationStandard;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public int getReadingSpeed() {
        return readingSpeed;
    }

    public void setReadingSpeed(int readingSpeed) {
        this.readingSpeed = readingSpeed;
    }

    public int getWritingSpeed() {
        return writingSpeed;
    }

    public void setWritingSpeed(int writingSpeed) {
        this.writingSpeed = writingSpeed;
    }


    public String getStorageTemperature() {
        return storageTemperature;
    }

    public void setStorageTemperature(String storageTemperature) {
        this.storageTemperature = storageTemperature;
    }

    public String getOperatingTemperature() {
        return operatingTemperature;
    }

    public void setOperatingTemperature(String operatingTemperature) {
        this.operatingTemperature = operatingTemperature;
    }

    public int getRandomReadingSpeed() {
        return randomReadingSpeed;
    }

    public void setRandomReadingSpeed(int randomReadingSpeed) {
        this.randomReadingSpeed = randomReadingSpeed;
    }

    public int getRandomWritingSpeed() {
        return randomWritingSpeed;
    }

    public void setRandomWritingSpeed(int randomWritingSpeed) {
        this.randomWritingSpeed = randomWritingSpeed;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

}
