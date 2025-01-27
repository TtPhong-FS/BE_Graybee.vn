package vn.graybee.requests.laptop;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.product.ProductCreateRequest;

public class LaptopDetailCreateRequest extends ProductCreateRequest {

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    @NotBlank(message = "Cannot be blank")
    private String demand;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @NotBlank(message = "Cannot be blank")
    private String cpu;

    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    @NotBlank(message = "Cannot be blank")
    private String ram;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @NotBlank(message = "Cannot be blank")
    private String storage;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("operating_system")
    private String operatingSystem;

    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    @NotBlank(message = "Cannot be blank")
    private String vga;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @NotBlank(message = "Cannot be blank")
    private String monitor;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @NotBlank(message = "Cannot be blank")
    private String ports;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    private String keyboard;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("wireless_connectivity")
    private String wirelessConnectivity;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    private String audio;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    private String webcam;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int battery;

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    private String material = "unknown";

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String confidentiality = "unknown";

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getVga() {
        return vga;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getWirelessConnectivity() {
        return wirelessConnectivity;
    }

    public void setWirelessConnectivity(String wirelessConnectivity) {
        this.wirelessConnectivity = wirelessConnectivity;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getWebcam() {
        return webcam;
    }

    public void setWebcam(String webcam) {
        this.webcam = webcam;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getConfidentiality() {
        return confidentiality;
    }

    public void setConfidentiality(String confidentiality) {
        this.confidentiality = confidentiality;
    }

}
