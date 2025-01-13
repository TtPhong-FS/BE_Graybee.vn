package vn.graybee.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rams")
public class Ram {

    @Id
    @Column(name = "product_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 5, name = "ram_type")
    private String ramType;

    @Column(nullable = false, length = 100, name = "series")
    private String series;

    @Column(name = "capacity")
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

    @Column(name = "heat_dissipation")
    private boolean heatDissipation;

    @Column(length = 10)
    private String led;

    public Ram() {
    }

    public Ram(Product product, String ramType, String series, int capacity, String type, int speed, String latency, float voltage, boolean ecc, boolean heatDissipation, String led) {
        this.product = product;
        this.ramType = ramType;
        this.series = series;
        this.capacity = capacity;
        this.type = type;
        this.speed = speed;
        this.latency = latency;
        this.voltage = voltage;
        this.ecc = ecc;
        this.heatDissipation = heatDissipation;
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

    public String getRamType() {
        return ramType;
    }

    public void setRamType(String ramType) {
        this.ramType = ramType;
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
        return heatDissipation;
    }

    public void setHeatDissipation(boolean heatDissipation) {
        this.heatDissipation = heatDissipation;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

}
