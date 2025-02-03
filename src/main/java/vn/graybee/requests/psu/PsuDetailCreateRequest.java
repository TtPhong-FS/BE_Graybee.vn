package vn.graybee.requests.psu;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.ProductCreateRequest;

public class PsuDetailCreateRequest extends ProductCreateRequest {

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("maximum_capacity")
    private int maximumCapacity;

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    private String material = "unknown";

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 20, message = "Must be between 1 and 20 characters")
    @JsonProperty("input_voltage")
    private String inputVoltage;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("input_current")
    private int inputCurrent = 0;

    @Size(min = 1, max = 20, message = "Must be between 1 and 20 characters")
    @JsonProperty("input_frequency")
    private String inputFrequency = "unknown";

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("fan_size")
    private int fanSize = 0;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("fan_speed")
    private int fanSpeed;

    @Size(min = 1, max = 20, message = "Must be between 1 and 20 characters")
    @JsonProperty("operating_temperature")
    private String operatingTemperature = "unknown";

    @Size(min = 1, max = 20, message = "Must be between 1 and 20 characters")
    private String signal = "unknown";

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    private int standing;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("hours_to_failure")
    private int hoursToFailure = 0;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("noise_level")
    private float noiseLevel = 0;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("percent_efficiency")
    private float percentEfficiency;

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

}
