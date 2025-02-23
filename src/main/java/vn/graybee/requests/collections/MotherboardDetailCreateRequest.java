package vn.graybee.requests.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.DetailDtoRequest;

public class MotherboardDetailCreateRequest extends DetailDtoRequest {

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    private String chipset;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    private String socket;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @JsonProperty("cpu_support")
    private String cpuSupport;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 500, message = "Must be between 1 and 500 characters")
    @JsonProperty("memory_support")
    private String memorySupport;

    @Size(min = 1, max = 500, message = "Must be between 1 and 500 characters")
    @JsonProperty("integrated_graphics")
    private String integratedGraphics;

    @Size(min = 1, max = 800, message = "Must be between 1 and 800 characters")
    @JsonProperty("sound_support")
    private String soundSupport;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @JsonProperty("expansion_slots")
    private String expansionSlots;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @JsonProperty("storage_support")
    private String storageSupport;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 300, message = "Must be between 1 and 300 characters")
    @JsonProperty("usb_support")
    private String usbSupport;

    @Size(min = 1, max = 300, message = "Must be between 1 and 300 characters")
    @JsonProperty("wireless_connectivity")
    private String wirelessConnectivity;

    @Size(min = 1, max = 150, message = "Must be between 1 and 150 characters")
    @JsonProperty("operating_system_support")
    private String operatingSystemSupport;

    @Size(min = 1, max = 600, message = "Must be between 1 and 600 characters")
    @JsonProperty("internal_input_output_connectivity")
    private String internalInputOutputConnectivity;

    @Size(min = 1, max = 400, message = "Must be between 1 and 400 characters")
    @JsonProperty("rear_input_output_connectivity")
    private String rearInputOutputConnectivity;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @JsonProperty("supporting_software")
    private String supportingSoftware;

    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String bios;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    private String accessory;

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getCpuSupport() {
        return cpuSupport;
    }

    public void setCpuSupport(String cpuSupport) {
        this.cpuSupport = cpuSupport;
    }

    public String getMemorySupport() {
        return memorySupport;
    }

    public void setMemorySupport(String memorySupport) {
        this.memorySupport = memorySupport;
    }

    public String getIntegratedGraphics() {
        return integratedGraphics;
    }

    public void setIntegratedGraphics(String integratedGraphics) {
        this.integratedGraphics = integratedGraphics;
    }

    public String getSoundSupport() {
        return soundSupport;
    }

    public void setSoundSupport(String soundSupport) {
        this.soundSupport = soundSupport;
    }

    public String getExpansionSlots() {
        return expansionSlots;
    }

    public void setExpansionSlots(String expansionSlots) {
        this.expansionSlots = expansionSlots;
    }

    public String getStorageSupport() {
        return storageSupport;
    }

    public void setStorageSupport(String storageSupport) {
        this.storageSupport = storageSupport;
    }

    public String getUsbSupport() {
        return usbSupport;
    }

    public void setUsbSupport(String usbSupport) {
        this.usbSupport = usbSupport;
    }

    public String getWirelessConnectivity() {
        return wirelessConnectivity;
    }

    public void setWirelessConnectivity(String wirelessConnectivity) {
        this.wirelessConnectivity = wirelessConnectivity;
    }

    public String getOperatingSystemSupport() {
        return operatingSystemSupport;
    }

    public void setOperatingSystemSupport(String operatingSystemSupport) {
        this.operatingSystemSupport = operatingSystemSupport;
    }

    public String getInternalInputOutputConnectivity() {
        return internalInputOutputConnectivity;
    }

    public void setInternalInputOutputConnectivity(String internalInputOutputConnectivity) {
        this.internalInputOutputConnectivity = internalInputOutputConnectivity;
    }

    public String getRearInputOutputConnectivity() {
        return rearInputOutputConnectivity;
    }

    public void setRearInputOutputConnectivity(String rearInputOutputConnectivity) {
        this.rearInputOutputConnectivity = rearInputOutputConnectivity;
    }

    public String getSupportingSoftware() {
        return supportingSoftware;
    }

    public void setSupportingSoftware(String supportingSoftware) {
        this.supportingSoftware = supportingSoftware;
    }

    public String getBios() {
        return bios;
    }

    public void setBios(String bios) {
        this.bios = bios;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

}
