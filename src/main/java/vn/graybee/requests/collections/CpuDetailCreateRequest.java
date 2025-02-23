package vn.graybee.requests.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.DetailDtoRequest;

public class CpuDetailCreateRequest extends DetailDtoRequest {

    @NotBlank(message = "Socket cannot be blank")
    @Size(min = 1, max = 35, message = "Must be between 1 and 35 characters")
    private String socket;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("multiplier")
    private int multiplier;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("number_of_streams")
    private int numberOfStreams;

    @NotNull(message = "Cannot be null")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("maximum_performance_core")
    private float maximumPerformanceCore;

    @NotNull(message = "Cannot be null")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("maximum_efficiency_core")
    private float maximumEfficiencyCore;

    @NotNull(message = "Cannot be null")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("base_performance_core")
    private float basePerformanceCore;

    @NotNull(message = "Cannot be null")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("base_efficiency_core")
    private float baseEfficiencyCore;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 20, message = "Must be between 1 and 20 characters")
    @JsonProperty("consumption")
    private String consumption;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    private int cache;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @JsonProperty("motherboard_compatible")
    private String motherboardCompatible;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("maximum_support_memory")
    private int maximumSupportMemory;

    @NotNull(message = "Cannot be null")
    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("maximum_bandwidth")
    private int maximumBandwidth;

    @NotBlank(message = "Cannot be blank")
    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @JsonProperty("memory_type")
    private String memoryType;

    @JsonProperty("is_graphics_core")
    private boolean isGraphicsCore = false;

    @Size(min = 1, max = 35, message = "Must be between 1 and 35 characters")
    @JsonProperty("pci_edition")
    private String pciEdition = "";

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @JsonProperty("pci_configuration")
    private String pciConfiguration = "";

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("maximum_pci_ports")
    private int maximumPciPorts = 0;

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getNumberOfStreams() {
        return numberOfStreams;
    }

    public void setNumberOfStreams(int numberOfStreams) {
        this.numberOfStreams = numberOfStreams;
    }

    public float getMaximumPerformanceCore() {
        return maximumPerformanceCore;
    }

    public void setMaximumPerformanceCore(float maximumPerformanceCore) {
        this.maximumPerformanceCore = maximumPerformanceCore;
    }

    public float getMaximumEfficiencyCore() {
        return maximumEfficiencyCore;
    }

    public void setMaximumEfficiencyCore(float maximumEfficiencyCore) {
        this.maximumEfficiencyCore = maximumEfficiencyCore;
    }

    public float getBasePerformanceCore() {
        return basePerformanceCore;
    }

    public void setBasePerformanceCore(float basePerformanceCore) {
        this.basePerformanceCore = basePerformanceCore;
    }

    public float getBaseEfficiencyCore() {
        return baseEfficiencyCore;
    }

    public void setBaseEfficiencyCore(float baseEfficiencyCore) {
        this.baseEfficiencyCore = baseEfficiencyCore;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public int getCache() {
        return cache;
    }

    public void setCache(int cache) {
        this.cache = cache;
    }

    public String getMotherboardCompatible() {
        return motherboardCompatible;
    }

    public void setMotherboardCompatible(String motherboardCompatible) {
        this.motherboardCompatible = motherboardCompatible;
    }

    public int getMaximumSupportMemory() {
        return maximumSupportMemory;
    }

    public void setMaximumSupportMemory(int maximumSupportMemory) {
        this.maximumSupportMemory = maximumSupportMemory;
    }

    public int getMaximumBandwidth() {
        return maximumBandwidth;
    }

    public void setMaximumBandwidth(int maximumBandwidth) {
        this.maximumBandwidth = maximumBandwidth;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    public boolean isGraphicsCore() {
        return isGraphicsCore;
    }

    public void setGraphicsCore(boolean graphicsCore) {
        isGraphicsCore = graphicsCore;
    }

    public String getPciEdition() {
        return pciEdition;
    }

    public void setPciEdition(String pciEdition) {
        this.pciEdition = pciEdition;
    }

    public String getPciConfiguration() {
        return pciConfiguration;
    }

    public void setPciConfiguration(String pciConfiguration) {
        this.pciConfiguration = pciConfiguration;
    }

    public int getMaximumPciPorts() {
        return maximumPciPorts;
    }

    public void setMaximumPciPorts(int maximumPciPorts) {
        this.maximumPciPorts = maximumPciPorts;
    }

}
