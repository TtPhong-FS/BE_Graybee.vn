package vn.graybee.models.collections;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ram_details")
public class RamDetail {

    @Id
    @Column(name = "product_id")
    private Long productId;

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

    @Column(name = "is_heat_dissipation")
    private boolean isHeatDissipation;

    @Column(length = 30)
    private String led;

    public RamDetail() {
    }

    public RamDetail(String suitableFor, String series, int capacity, String type, int speed, String latency, float voltage, boolean isHeatDissipation, String led) {
        this.suitableFor = suitableFor;
        this.series = series;
        this.capacity = capacity;
        this.type = type;
        this.speed = speed;
        this.latency = latency;
        this.voltage = voltage;
        this.isHeatDissipation = isHeatDissipation;
        this.led = led;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
