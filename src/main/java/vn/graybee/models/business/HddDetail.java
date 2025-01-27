package vn.graybee.models.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "hdd_details")
public class HddDetail {

    @Id
    @Column(name = "product_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "communication_standard", length = 50, nullable = false)
    private String communicationStandard;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "hours_to_failure")
    private int hoursToFailure;

    @Column(name = "reading_speed", nullable = false)
    private int readingSpeed;

    @Column(name = "writing_speed", nullable = false)
    private int writingSpeed;

    @Column(name = "memory_technology", nullable = false, length = 50)
    private String memoryTechnology;

    @Column(name = "noise_level", nullable = false)
    private float noiseLevel;

    private int cache;

    @Column(name = "revolution_per_minutes")
    private int revolutionPerMinutes;

    public HddDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public int getHoursToFailure() {
        return hoursToFailure;
    }

    public void setHoursToFailure(int hoursToFailure) {
        this.hoursToFailure = hoursToFailure;
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

    public String getMemoryTechnology() {
        return memoryTechnology;
    }

    public void setMemoryTechnology(String memoryTechnology) {
        this.memoryTechnology = memoryTechnology;
    }

    public float getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(float noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public int getCache() {
        return cache;
    }

    public void setCache(int cache) {
        this.cache = cache;
    }

    public int getRevolutionPerMinutes() {
        return revolutionPerMinutes;
    }

    public void setRevolutionPerMinutes(int revolutionPerMinutes) {
        this.revolutionPerMinutes = revolutionPerMinutes;
    }

    public HddDetail(Product product, String communicationStandard, int capacity, int hoursToFailure, int readingSpeed, int writingSpeed, String memoryTechnology, float noiseLevel, int cache, int revolutionPerMinutes) {
        this.product = product;
        this.communicationStandard = communicationStandard;
        this.capacity = capacity;
        this.hoursToFailure = hoursToFailure;
        this.readingSpeed = readingSpeed;
        this.writingSpeed = writingSpeed;
        this.memoryTechnology = memoryTechnology;
        this.noiseLevel = noiseLevel;
        this.cache = cache;
        this.revolutionPerMinutes = revolutionPerMinutes;
    }

}
