package vn.graybee.requests.heatDissipation;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.DetailDtoRequest;

public class HeatDissipationDetailCreateRequest extends DetailDtoRequest {

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @JsonProperty("cpu_socket")
    private String cpuSocket;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String series;

    @Size(min = 1, max = 40, message = "Must be between 1 and 40 characters")
    @JsonProperty("heat_sink_material")
    private String heatSinkMaterial = "";

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int speed;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private float airflow = 0;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("air_pressure")
    private float airPressure = 0;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("noise_level")
    private int noiseLevel = 0;

    @JsonProperty("is_application_control")
    private boolean isApplicationControl = false;

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    private String led = "";

    public String getCpuSocket() {
        return cpuSocket;
    }

    public void setCpuSocket(String cpuSocket) {
        this.cpuSocket = cpuSocket;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getHeatSinkMaterial() {
        return heatSinkMaterial;
    }

    public void setHeatSinkMaterial(String heatSinkMaterial) {
        this.heatSinkMaterial = heatSinkMaterial;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getAirflow() {
        return airflow;
    }

    public void setAirflow(float airflow) {
        this.airflow = airflow;
    }

    public float getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(float airPressure) {
        this.airPressure = airPressure;
    }

    public int getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(int noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public boolean isApplicationControl() {
        return isApplicationControl;
    }

    public void setApplicationControl(boolean applicationControl) {
        isApplicationControl = applicationControl;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

}
