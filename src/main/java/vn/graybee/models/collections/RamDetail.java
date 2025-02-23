package vn.graybee.models.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import vn.graybee.models.products.Product;

@Entity
@Table(name = "ram_details")
public class RamDetail {

    @Id
    @Column(name = "product_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 5, name = "suitable_for")
    private String suitableFor;

    @Column(nullable = false, length = 100, name = "series")
    private String series;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(nullable = false, length = 10)
    private String type;

    @Column(nullable = false)
    private int speed;

    @Column(length = 50)
    private String latency;

    @Column(name = "voltage")
    private float voltage;

    private boolean ecc;

    @Column(name = "is_heat_dissipation")
    private boolean isHeatDissipation;

    @Column(length = 30)
    private String led;

    public RamDetail() {
    }

    public RamDetail(Product product, String suitableFor, String series, int capacity, String type, int speed, String latency, float voltage, boolean ecc, boolean isHeatDissipation, String led) {
        this.product = product;
        this.suitableFor = suitableFor;
        this.series = series;
        this.capacity = capacity;
        this.type = type;
        this.speed = speed;
        this.latency = latency;
        this.voltage = voltage;
        this.ecc = ecc;
        this.isHeatDissipation = isHeatDissipation;
        this.led = led;
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

    public String getSuitableFor() {
        return suitableFor;
    }

    public void setSuitableFor(String suitableFor) {
        this.suitableFor = suitableFor;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public boolean isEcc() {
        return ecc;
    }

    public void setEcc(boolean ecc) {
        this.ecc = ecc;
    }

    public boolean isHeatDissipation() {
        return isHeatDissipation;
    }

    public void setHeatDissipation(boolean heatDissipation) {
        isHeatDissipation = heatDissipation;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

}
