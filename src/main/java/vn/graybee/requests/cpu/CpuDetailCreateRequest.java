package vn.graybee.requests.cpu;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class CpuDetailCreateRequest {

    @Size(min = 1, max = 35, message = "Socket must be between 1 and 35 characters")
    @NotBlank(message = "Socket cannot be blank")
    private String socket;

    @NotNull(message = "Multiplier cannot be null")
    @PositiveOrZero(message = "Multiplier cannot be negative")
    @JsonProperty("multiplier")
    private int multiplier;

    @NotNull(message = "Number of Streams cannot be null")
    @PositiveOrZero(message = "Number of Streams cannot be negative")
    @JsonProperty("number_of_streams")
    private int numberOfStreams;

    @NotNull(message = "Maximum Performance core cannot be null")
    @PositiveOrZero(message = "Maximum Performance core cannot be negative")
    @JsonProperty("maximum_performance_core")
    private float maximumPerformanceCore;

    @NotNull(message = "Maximum Efficiency core cannot be null")
    @PositiveOrZero(message = "Maximum Efficiency core cannot be negative")
    @JsonProperty("maximum_efficiency_core")
    private float maximumEfficiencyCore;

    @NotNull(message = "Base Performance core cannot be null")
    @PositiveOrZero(message = "Base Performance core cannot be negative")
    @JsonProperty("base_performance_core")
    private float basePerformanceCore;

    @NotNull(message = "Base Efficiency core cannot be null")
    @PositiveOrZero(message = "Base Efficiency core cannot be negative")
    @JsonProperty("base_efficiency_core")
    private float baseEfficiencyCore;

    @Size(min = 1, max = 20, message = "Consumption must be between 1 and 20 characters")
    @NotBlank(message = "Consumption cannot be blank")
    @JsonProperty("consumption")
    private String consumption;

    @NotNull(message = "Cache cannot be null")
    @PositiveOrZero(message = "Cache cannot be negative")
    private int cache;

    @Size(min = 1, max = 50, message = "Motherboard compatible must be between 1 and 50 characters")
    @NotBlank(message = "Motherboard compatible cannot be blank")
    @JsonProperty("motherboard_compatible")
    private String motherboardCompatible;

    @NotNull(message = "Maximum support memory cannot be null")
    @PositiveOrZero(message = "Maximum support memory cannot be negative")
    @JsonProperty("maximum_support_memory")
    private int maximumSupportMemory;

    @NotNull(message = "Maximum bandwidth cannot be null")
    @PositiveOrZero(message = "Maximum bandwidth cannot be negative")
    @JsonProperty("maximum_bandwidth")
    private int maximumBandwidth;

    @Size(min = 1, max = 50, message = "Memory type must be between 1 and 50 characters")
    @NotBlank(message = "Memory type cannot be blank")
    @JsonProperty("memory_type")
    private String memoryType;

    @JsonProperty("is_graphics_core")
    private boolean isGraphicsCore = false;

    @Size(min = 1, max = 35, message = "Pci edition info must be between 1 and 35 characters")
    @JsonProperty("pci_edition")
    private String pciEdition = "unknown";

    @Size(min = 1, max = 50, message = "Pci configuration info must be between 1 and 50 characters")
    @JsonProperty("pci_configuration")
    private String pciConfiguration = "unknown";

    @PositiveOrZero(message = "Maximum Pci port cannot be negative")
    @JsonProperty("maximum_pci_ports")
    private int maximumPciPorts = 0;

}
