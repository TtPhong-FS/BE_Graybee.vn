package vn.graybee.models.collections;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import vn.graybee.models.products.Product;

@Entity
@Table(name = "cpu_details")
public class CpuDetail {


    @Id
    @Column(name = "product_id")
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 35)
    private String socket;

    @Column(name = "multiplier", nullable = false)
    private int multiplier;

    @Column(name = "number_of_streams", nullable = false)
    private int numberOfStreams;

    @Column(name = "maximum_performance_core", nullable = false)
    private float maximumPerformanceCore;

    @Column(name = "maximum_efficiency_core", nullable = false)
    private float maximumEfficiencyCore;

    @Column(name = "base_performance_core", nullable = false)
    private float basePerformanceCore;

    @Column(name = "base_efficiency_core", nullable = false)
    private float baseEfficiencyCore;

    @Column(name = "power_consumption", nullable = false)
    private float powerConsumption;

    @Column(nullable = false)
    private int cache;

    @Column(name = "motherboard_compatible", length = 50, nullable = false)
    private String motherboardCompatible;

    @Column(name = "maximum_bandwidth", nullable = false)
    private int maximumBandwidth;

    @Column(name = "memory_type", nullable = false, length = 50)
    private String memoryType;

    @Column(name = "is_graphics_core")
    private boolean isGraphicsCore;

    public CpuDetail() {
    }

    public CpuDetail(Long productId, String socket, int multiplier, int numberOfStreams, float maximumPerformanceCore, float maximumEfficiencyCore, float basePerformanceCore, float baseEfficiencyCore, float powerConsumption, int cache, String motherboardCompatible, int maximumBandwidth, String memoryType, boolean isGraphicsCore) {
        this.productId = productId;
        this.socket = socket;
        this.multiplier = multiplier;
        this.numberOfStreams = numberOfStreams;
        this.maximumPerformanceCore = maximumPerformanceCore;
        this.maximumEfficiencyCore = maximumEfficiencyCore;
        this.basePerformanceCore = basePerformanceCore;
        this.baseEfficiencyCore = baseEfficiencyCore;
        this.powerConsumption = powerConsumption;
        this.cache = cache;
        this.motherboardCompatible = motherboardCompatible;
        this.maximumBandwidth = maximumBandwidth;
        this.memoryType = memoryType;
        this.isGraphicsCore = isGraphicsCore;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

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
