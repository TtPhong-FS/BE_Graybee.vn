package vn.graybee.requests.collections;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.DetailDtoRequest;

public class CpuDetailCreateRequest extends DetailDtoRequest {

    @NotBlank(message = "Socket không thể trống")
    @Size(min = 5, max = 35, message = "Độ dài ít nhất từ 5 đến 35 ký tự")
    private String socket;

    @NotNull(message = "Nhập số nhân")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private int multiplier;

    @NotNull(message = "Nhập số luồng")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private int numberOfStreams;

    @NotNull(message = "Nhập hiệu suất tối đa P-Core")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private float maximumPerformanceCore;

    @NotNull(message = "Nhập hiệu suất tối đa E-Core")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private float maximumEfficiencyCore;

    @NotNull(message = "Nhập hiệu suất ca bản P-Core")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private float basePerformanceCore;

    @NotNull(message = "Nhập hiệu suất ca bản E-Core")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private float baseEfficiencyCore;

    @NotNull(message = "Nhập nguồn điện tiêu hao")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private float powerConsumption;

    @NotNull(message = "Nhập bộ nhớ đệm")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private int cache;

    @NotBlank(message = "Nhập series bo mạch chủ tương thích")
    @Size(min = 5, max = 50, message = "Độ dài ít nhất từ 5 đến 50 ký tự")
    private String motherboardCompatible;

    @NotNull(message = "Nhập bộ nhớ tối đa")
    @PositiveOrZero(message = "Vui lòng nhập số dương")
    private int maximumBandwidth;

    @NotBlank(message = "Nhập loại RAM")
    @Size(min = 1, max = 50, message = "Độ dài ít nhất từ 5 đến 50 ký tự")
    private String memoryType;

    private boolean isGraphicsCore;

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

    public float getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(float powerConsumption) {
        this.powerConsumption = powerConsumption;
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

}
