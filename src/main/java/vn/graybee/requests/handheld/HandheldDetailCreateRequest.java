package vn.graybee.requests.handheld;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.product.ProductCreateRequest;

public class HandheldDetailCreateRequest extends ProductCreateRequest {

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("connect_mode")
    private String connectMode;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("number_of_nodes")
    private int numberOfNodes;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String support = "unknown";

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("usage_time")
    private int usageTime;

    @Size(min = 1, max = 40, message = "Must be between 1 and 40 characters")
    @NotBlank(message = "Cannot be blank")
    private String battery;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String charging = "unknown";

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    private String led = "unknown";

    public String getConnectMode() {
        return connectMode;
    }

    public void setConnectMode(String connectMode) {
        this.connectMode = connectMode;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public int getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(int usageTime) {
        this.usageTime = usageTime;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getCharging() {
        return charging;
    }

    public void setCharging(String charging) {
        this.charging = charging;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

}
