package vn.graybee.requests.motherboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.ProductCreateRequest;

public class MotherboardDetailCreateRequest extends ProductCreateRequest {

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    @NotBlank(message = "Cannot be blank")
    private String chipset;

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    @NotBlank(message = "Cannot be blank")
    private String socket;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("cpu_support")
    private String cpuSupport;

    @Size(min = 1, max = 80, message = "Must be between 1 and 80 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("technology_support")
    private String technologySupport;

    @Size(min = 1, max = 500, message = "Must be between 1 and 500 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("memory_support")
    private String memorySupport;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("slot_memory_support")
    private String slotMemorySupport;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("maximum_memory_support")
    private int maximumMemorySupport;

    @Size(min = 1, max = 500, message = "Must be between 1 and 500 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("integrated_graphics")
    private String integratedGraphics;

    @Size(min = 1, max = 800, message = "Must be between 1 and 800 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("sound_support")
    private String soundSupport;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("lan_support")
    private String lanSupport;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("expansion_slots")
    private String expansionSlots;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("storage_support")
    private String storageSupport;

    @Size(min = 1, max = 300, message = "Must be between 1 and 300 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("usb_support")
    private String usbSupport;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @JsonProperty("wireless_connectivity")
    private String wirelessConnectivity = "none";

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("operating_system_support")
    private String operatingSystemSupport;

    @Size(min = 1, max = 500, message = "Must be between 1 and 500 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("internal_input_output_connectivity")
    private String internalInputOutputConnectivity;

    @Size(min = 1, max = 800, message = "Must be between 1 and 800 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("internal_input_connectivity")
    private String internalInputConnectivity;

    @Size(min = 1, max = 800, message = "Must be between 1 and 800 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("internal_output_connectivity")
    private String internalOutputConnectivity;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("rear_input_connectivity")
    private String rearInputConnectivity;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("rear_output_connectivity")
    private String rearOutputConnectivity;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("rear_input_output_connectivity")
    private String rearInputOutputConnectivity;

    @Size(min = 1, max = 300, message = "Must be between 1 and 300 characters")
    @JsonProperty("system_monitoring_application")
    private String systemMonitoringApplication = "unknown";

    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    private String bios = "unknown";

    @Size(min = 1, max = 1000, message = "Must be between 1 and 1000 characters")
    @JsonProperty("special_features")
    private String specialFeatures = "unknown";

    @Size(min = 1, max = 1000, message = "Must be between 1 and 1000 characters")
    @JsonProperty("unique_features")
    private String uniqueFeatures = "unknown";

    @Size(min = 1, max = 300, message = "Must be between 1 and 300 characters")
    private String accessory = "unknown";

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

    public String getTechnologySupport() {
        return technologySupport;
    }

    public void setTechnologySupport(String technologySupport) {
        this.technologySupport = technologySupport;
    }

    public String getMemorySupport() {
        return memorySupport;
    }

    public void setMemorySupport(String memorySupport) {
        this.memorySupport = memorySupport;
    }

    public String getSlotMemorySupport() {
        return slotMemorySupport;
    }

    public void setSlotMemorySupport(String slotMemorySupport) {
        this.slotMemorySupport = slotMemorySupport;
    }

    public int getMaximumMemorySupport() {
        return maximumMemorySupport;
    }

    public void setMaximumMemorySupport(int maximumMemorySupport) {
        this.maximumMemorySupport = maximumMemorySupport;
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

    public String getLanSupport() {
        return lanSupport;
    }

    public void setLanSupport(String lanSupport) {
        this.lanSupport = lanSupport;
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

    public String getInternalInputConnectivity() {
        return internalInputConnectivity;
    }

    public void setInternalInputConnectivity(String internalInputConnectivity) {
        this.internalInputConnectivity = internalInputConnectivity;
    }

    public String getInternalOutputConnectivity() {
        return internalOutputConnectivity;
    }

    public void setInternalOutputConnectivity(String internalOutputConnectivity) {
        this.internalOutputConnectivity = internalOutputConnectivity;
    }

    public String getRearInputConnectivity() {
        return rearInputConnectivity;
    }

    public void setRearInputConnectivity(String rearInputConnectivity) {
        this.rearInputConnectivity = rearInputConnectivity;
    }

    public String getRearOutputConnectivity() {
        return rearOutputConnectivity;
    }

    public void setRearOutputConnectivity(String rearOutputConnectivity) {
        this.rearOutputConnectivity = rearOutputConnectivity;
    }

    public String getRearInputOutputConnectivity() {
        return rearInputOutputConnectivity;
    }

    public void setRearInputOutputConnectivity(String rearInputOutputConnectivity) {
        this.rearInputOutputConnectivity = rearInputOutputConnectivity;
    }

    public String getSystemMonitoringApplication() {
        return systemMonitoringApplication;
    }

    public void setSystemMonitoringApplication(String systemMonitoringApplication) {
        this.systemMonitoringApplication = systemMonitoringApplication;
    }

    public String getBios() {
        return bios;
    }

    public void setBios(String bios) {
        this.bios = bios;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public String getUniqueFeatures() {
        return uniqueFeatures;
    }

    public void setUniqueFeatures(String uniqueFeatures) {
        this.uniqueFeatures = uniqueFeatures;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

}
