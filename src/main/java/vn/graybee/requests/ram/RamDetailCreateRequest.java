package vn.graybee.requests.ram;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.product.ProductCreateRequest;

public class RamDetailCreateRequest extends ProductCreateRequest {

    @NotEmpty(message = "Ram's ram type cannot be empty")
    @Size(min = 1, max = 5, message = "Ram Type must be between 2 and 5 characters")
    @JsonProperty("ram_type")
    private String ramType;

    @NotEmpty(message = "Ram's series cannot be empty")
    @Size(min = 1, max = 100, message = "Ram Type must be between 0 and 100 characters")
    private String series;

    @NotNull(message = "Ram's capacity cannot be null")
    @PositiveOrZero(message = "Capacity cannot be negative")
    private int capacity;

    @NotEmpty(message = "Ram's type cannot be empty")
    private String type;

    @NotNull(message = "Ram's speed cannot be null")
    @PositiveOrZero(message = "Speed cannot be negative")
    private int speed;

    @Size(min = 1, max = 50, message = "Latency must be between 0 and 50 characters")
    private String latency = "unknown";

    @JsonProperty("voltage")
    private float voltage = 0;

    private boolean ecc = false;

    @JsonProperty("heat_dissipation")
    private boolean heatDissipation = false;

    private String led = "unknown";

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
