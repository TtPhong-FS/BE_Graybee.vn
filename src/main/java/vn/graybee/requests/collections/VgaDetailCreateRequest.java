package vn.graybee.requests.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.DetailDtoRequest;

public class VgaDetailCreateRequest extends DetailDtoRequest {

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("memory_speed")
    private int memorySpeed;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("memory_protocol")
    private int memoryProtocol;

    @Size(min = 1, max = 20, message = "Must be between 1 and 20 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("maximum_resolution")
    private String maximumResolution;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("multiple_screen")
    private int multipleScreen = 3;

    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("protocols")
    private String protocols;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("gpu_clock")
    private int gpuClock;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("bus_standard")
    private String busStandard;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("number_of_processing_unit")
    private int numberOfProcessingUnit;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("power_consumption")
    private int powerConsumption;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("psu_recommend")
    private int psuRecommend;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("directx")
    private int directx = 11;

    @JsonProperty("is_application_support")
    private boolean isApplicationSupport = false;

    public int getMemorySpeed() {
        return memorySpeed;
    }

    public void setMemorySpeed(int memorySpeed) {
        this.memorySpeed = memorySpeed;
    }

    public int getMemoryProtocol() {
        return memoryProtocol;
    }

    public void setMemoryProtocol(int memoryProtocol) {
        this.memoryProtocol = memoryProtocol;
    }

    public String getMaximumResolution() {
        return maximumResolution;
    }

    public void setMaximumResolution(String maximumResolution) {
        this.maximumResolution = maximumResolution;
    }

    public int getMultipleScreen() {
        return multipleScreen;
    }

    public void setMultipleScreen(int multipleScreen) {
        this.multipleScreen = multipleScreen;
    }

    public String getProtocols() {
        return protocols;
    }

    public void setProtocols(String protocols) {
        this.protocols = protocols;
    }

    public int getGpuClock() {
        return gpuClock;
    }

    public void setGpuClock(int gpuClock) {
        this.gpuClock = gpuClock;
    }

    public String getBusStandard() {
        return busStandard;
    }

    public void setBusStandard(String busStandard) {
        this.busStandard = busStandard;
    }

    public int getNumberOfProcessingUnit() {
        return numberOfProcessingUnit;
    }

    public void setNumberOfProcessingUnit(int numberOfProcessingUnit) {
        this.numberOfProcessingUnit = numberOfProcessingUnit;
    }

    public int getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(int powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public int getPsuRecommend() {
        return psuRecommend;
    }

    public void setPsuRecommend(int psuRecommend) {
        this.psuRecommend = psuRecommend;
    }

    public int getDirectx() {
        return directx;
    }

    public void setDirectx(int directx) {
        this.directx = directx;
    }

    public boolean isApplicationSupport() {
        return isApplicationSupport;
    }

    public void setApplicationSupport(boolean applicationSupport) {
        isApplicationSupport = applicationSupport;
    }

}
