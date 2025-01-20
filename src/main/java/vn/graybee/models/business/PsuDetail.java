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
@Table(name = "psu_details")
public class PsuDetail {

    @Id
    @Column(name = "product_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "maximum_capacity")
    private int maximumCapacity;

    @Column(length = 30)
    private String material;

    @Column(name = "input_voltage", length = 20, nullable = false)
    private String inputVoltage;

    @Column(name = "input_current")
    private int inputCurrent;

    @Column(name = "input_frequency", length = 20)
    private String inputFrequency;

    @Column(name = "fan_size")
    private int fanSize;

    @Column(name = "fan_speed")
    private int fanSpeed;

    @Column(name = "operating_temperature", length = 20)
    private String operatingTemperature;

    @Column(length = 20)
    private String signal;

    private int standing;

    @Column(name = "hours_to_failure")
    private int hoursToFailure;

    @Column(name = "noise_level")
    private float noiseLevel;

    @Column(name = "percent_efficiency")
    private float percentEfficiency;

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

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getInputVoltage() {
        return inputVoltage;
    }

    public void setInputVoltage(String inputVoltage) {
        this.inputVoltage = inputVoltage;
    }

    public int getInputCurrent() {
        return inputCurrent;
    }

    public void setInputCurrent(int inputCurrent) {
        this.inputCurrent = inputCurrent;
    }

    public String getInputFrequency() {
        return inputFrequency;
    }

    public void setInputFrequency(String inputFrequency) {
        this.inputFrequency = inputFrequency;
    }

    public int getFanSize() {
        return fanSize;
    }

    public void setFanSize(int fanSize) {
        this.fanSize = fanSize;
    }

    public int getFanSpeed() {
        return fanSpeed;
    }

    public void setFanSpeed(int fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public String getOperatingTemperature() {
        return operatingTemperature;
    }

    public void setOperatingTemperature(String operatingTemperature) {
        this.operatingTemperature = operatingTemperature;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public int getStanding() {
        return standing;
    }

    public void setStanding(int standing) {
        this.standing = standing;
    }

    public int getHoursToFailure() {
        return hoursToFailure;
    }

    public void setHoursToFailure(int hoursToFailure) {
        this.hoursToFailure = hoursToFailure;
    }

    public float getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(float noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public float getPercentEfficiency() {
        return percentEfficiency;
    }

    public void setPercentEfficiency(float percentEfficiency) {
        this.percentEfficiency = percentEfficiency;
    }

    public PsuDetail(Long id, int maximumCapacity, String material, String inputVoltage, int inputCurrent, String inputFrequency, int fanSize, int fanSpeed, String operatingTemperature, String signal, int standing, int hoursToFailure, float noiseLevel, float percentEfficiency) {
        this.id = id;
        this.maximumCapacity = maximumCapacity;
        this.material = material;
        this.inputVoltage = inputVoltage;
        this.inputCurrent = inputCurrent;
        this.inputFrequency = inputFrequency;
        this.fanSize = fanSize;
        this.fanSpeed = fanSpeed;
        this.operatingTemperature = operatingTemperature;
        this.signal = signal;
        this.standing = standing;
        this.hoursToFailure = hoursToFailure;
        this.noiseLevel = noiseLevel;
        this.percentEfficiency = percentEfficiency;
    }

    public PsuDetail() {
    }

}
