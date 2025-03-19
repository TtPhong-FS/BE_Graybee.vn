package vn.graybee.requests.collections;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.DetailDtoRequest;

public class RamDetailCreateRequest extends DetailDtoRequest {


    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 5, message = "Must be between 1 and 5 characters")
    private String suitableFor;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String series;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int capacity;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 10, message = "Must be between 1 and 10 characters")
    private String type;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int speed;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String latency;

    @PositiveOrZero(message = "Cannot be a negative number")
    private float voltage;

    private boolean isHeatDissipation;

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    private String led;

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
