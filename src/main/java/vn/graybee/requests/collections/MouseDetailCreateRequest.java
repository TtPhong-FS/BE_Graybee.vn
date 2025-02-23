package vn.graybee.requests.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.DetailDtoRequest;

public class MouseDetailCreateRequest extends DetailDtoRequest {

    private String sensors;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("number_of_nodes")
    private int numberOfNodes;

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    @JsonProperty("switch_type")
    private String switchType = "";

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    @JsonProperty("switch_life")
    private String switchLife;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("polling_rate")
    private int pollingRate;

    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String software = "";

    @Size(min = 1, max = 40, message = "Must be between 1 and 40 characters")
    @NotBlank(message = "Cannot be blank")
    private String connect;

    @JsonProperty("is_wireless_connect")
    private boolean isWirelessConnect = false;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String battery = "";

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    private String led = "";

    public String getSensors() {
        return sensors;
    }

    public void setSensors(String sensors) {
        this.sensors = sensors;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }

    public String getSwitchType() {
        return switchType;
    }

    public void setSwitchType(String switchType) {
        this.switchType = switchType;
    }

    public String getSwitchLife() {
        return switchLife;
    }

    public void setSwitchLife(String switchLife) {
        this.switchLife = switchLife;
    }

    public int getPollingRate() {
        return pollingRate;
    }

    public void setPollingRate(int pollingRate) {
        this.pollingRate = pollingRate;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public boolean isWirelessConnect() {
        return isWirelessConnect;
    }

    public void setWirelessConnect(boolean wirelessConnect) {
        isWirelessConnect = wirelessConnect;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

}

