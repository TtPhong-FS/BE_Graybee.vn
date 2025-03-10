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
    @JsonProperty("memory_bus")
    private int memoryBus;

    @Size(min = 1, max = 30, message = "Must be between 1 and 20 characters")
    @NotBlank(message = "Cannot be blank")
    private String memory;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @NotNull(message = "Cannot be null")
    @JsonProperty("cuda_kernel")
    private int cudaKernel;

    @Size(min = 1, max = 20, message = "Must be between 1 and 20 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("maximum_resolution")
    private String maximumResolution;

    @Positive(message = "Must be a positive number")
    @PositiveOrZero(message = "Cannot be a negative number")
    @JsonProperty("maximum_screen")
    private int maximumScreen;

    @Size(min = 1, max = 200, message = "Must be between 1 and 100 characters")
    @NotBlank(message = "Cannot be blank")
    private String ports;

    @Size(min = 1, max = 200, message = "Must be between 1 and 100 characters")
    @NotNull(message = "Cannot be null")
    private String clock;

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

    @JsonProperty("is_application_support")
    private boolean isApplicationSupport;

    public int getMemorySpeed() {
        return memorySpeed;
    }

    public void setMemorySpeed(int memorySpeed) {
        this.memorySpeed = memorySpeed;
    }

    public int getMemoryBus() {
        return memoryBus;
    }

    public void setMemoryBus(int memoryBus) {
        this.memoryBus = memoryBus;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public int getCudaKernel() {
        return cudaKernel;
    }

    public void setCudaKernel(int cudaKernel) {
        this.cudaKernel = cudaKernel;
    }

    public String getMaximumResolution() {
        return maximumResolution;
    }

    public void setMaximumResolution(String maximumResolution) {
        this.maximumResolution = maximumResolution;
    }

    public int getMaximumScreen() {
        return maximumScreen;
    }

    public void setMaximumScreen(int maximumScreen) {
        this.maximumScreen = maximumScreen;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
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

    public boolean isApplicationSupport() {
        return isApplicationSupport;
    }

    public void setApplicationSupport(boolean applicationSupport) {
        isApplicationSupport = applicationSupport;
    }

}
