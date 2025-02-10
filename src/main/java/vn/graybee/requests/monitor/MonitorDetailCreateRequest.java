package vn.graybee.requests.monitor;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.DetailDtoRequest;

public class MonitorDetailCreateRequest extends DetailDtoRequest {

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("screen_size")
    private int screenSize;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 20, message = "Must be between 1 and 20 characters")
    @JsonProperty("screen_type")
    private String screenType;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    private String panels;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 10, message = "Must be between 1 and 10 characters")
    @JsonProperty("aspect_ratio")
    private String aspectRatio;

    @JsonProperty("is_speaker")
    private boolean isSpeaker = false;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 40, message = "Must be between 1 and 40 characters")
    private String resolution;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("color_display")
    private int colorDisplay;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("percent_color")
    private int percentColor;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("refresh_rate")
    private int refreshRate = 0;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String ports;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("power_consumption")
    private int powerConsumption = 0;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("power_save_mode")
    private int powerSaveMode = 0;

    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("power_off_mode")
    private float powerOffMode = 0;

    @PositiveOrZero(message = "Cannot be a negative number")
    private float voltage = 0;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @JsonProperty("special_feature")
    private String specialFeature = "";

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("hours_to_failure")
    private int hoursToFailure = 0;

    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String accessory = "";

    public int getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(int screenSize) {
        this.screenSize = screenSize;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getPanels() {
        return panels;
    }

    public void setPanels(String panels) {
        this.panels = panels;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public boolean isSpeaker() {
        return isSpeaker;
    }

    public void setSpeaker(boolean speaker) {
        isSpeaker = speaker;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getColorDisplay() {
        return colorDisplay;
    }

    public void setColorDisplay(int colorDisplay) {
        this.colorDisplay = colorDisplay;
    }

    public int getPercentColor() {
        return percentColor;
    }

    public void setPercentColor(int percentColor) {
        this.percentColor = percentColor;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public int getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(int powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public int getPowerSaveMode() {
        return powerSaveMode;
    }

    public void setPowerSaveMode(int powerSaveMode) {
        this.powerSaveMode = powerSaveMode;
    }

    public float getPowerOffMode() {
        return powerOffMode;
    }

    public void setPowerOffMode(float powerOffMode) {
        this.powerOffMode = powerOffMode;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public String getSpecialFeature() {
        return specialFeature;
    }

    public void setSpecialFeature(String specialFeature) {
        this.specialFeature = specialFeature;
    }

    public int getHoursToFailure() {
        return hoursToFailure;
    }

    public void setHoursToFailure(int hoursToFailure) {
        this.hoursToFailure = hoursToFailure;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

}
