package vn.graybee.requests.vga;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class VgaDetailCreateRequest {

    @NotNull(message = "Memory speed cannot be null")
    @JsonProperty("memory_speed")
    private int memorySpeed;

    @NotNull(message = "Memory speed cannot be null")
    @JsonProperty("memory_protocol")
    private int memoryProtocol;

    @Size(min = 1, max = 20, message = "Maximum resolution must be between 1 and 20 characters")
    @NotBlank(message = "Maximum resolution cannot be blank")
    @JsonProperty("maximum_resolution")
    private String maximumResolution;

    @JsonProperty("multiple_screen")
    private int multipleScreen = 3;

    @Size(min = 1, max = 100, message = "Protocols must be between 1 and 100 characters")
    @NotBlank(message = "Protocols cannot be blank")
    @JsonProperty("protocols")
    private String protocols;

    @NotNull(message = "Memory speed cannot be null")
    @JsonProperty("gpu_clock")
    private int gpuClock;

    @Size(min = 1, max = 50, message = "Bus standard must be between 1 and 50 characters")
    @NotBlank(message = "Bus standard cannot be blank")
    @JsonProperty("bus_standard")
    private String busStandard;

    @NotNull(message = "Memory speed cannot be null")
    @JsonProperty("number_of_processing_unit")
    private int numberOfProcessingUnit;

    @NotNull(message = "Power consumption cannot be null")
    @JsonProperty("power_consumption")
    private int powerConsumption;

    @NotNull(message = "Psu recommend cannot be null")
    @JsonProperty("psu_recommend")
    private int psuRecommend;

    @JsonProperty("directx")
    private int directx = 11;

    @JsonProperty("is_application_support")
    private boolean isApplicationSupport = false;

}
