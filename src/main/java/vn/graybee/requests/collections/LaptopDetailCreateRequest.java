package vn.graybee.requests.collections;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.DetailDtoRequest;

public class LaptopDetailCreateRequest extends DetailDtoRequest {

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    private String demand;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    private String cpu;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String ram;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    private String storage;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String operatingSystem;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String vga;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    private String monitor;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    private String ports;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String keyboard;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String wirelessConnectivity;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String audio;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String webcam;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int battery;

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    private String material;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    private String confidentiality;

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
