package vn.graybee.models.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cpu_details")
public class CpuDetail {

    @Id
    @Column(name = "product_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
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

    @Column(name = "consumption", length = 20, nullable = false)
    private String consumption;

    @Column(nullable = false)
    private int cache;

    @Column(name = "motherboard_compatible", length = 50, nullable = false)
    private String motherboardCompatible;

    @Column(name = "maximum_support_memory", nullable = false)
    private int maximumSupportMemory;

    @Column(name = "maximum_bandwidth", nullable = false)
    private int maximumBandwidth;

    @Column(name = "memory_type", nullable = false, length = 50)
    private String memoryType;

    @Column(name = "is_graphics_core")
    private boolean isGraphicsCore;

    @Column(name = "pci_edition", length = 35)
    private String pciEdition;

    @Column(name = "pci_configuration", length = 50)
    private String pciConfiguration;

    @Column(name = "maximum_pci_ports")
    private int maximumPciPorts;

    public CpuDetail() {
    }

    public CpuDetail(Product product, String socket, int multiplier, int numberOfStreams, float maximumPerformanceCore, float maximumEfficiencyCore, float basePerformanceCore, float baseEfficiencyCore, String consumption, int cache, String motherboardCompatible, int maximumSupportMemory, int maximumBandwidth, String memoryType, boolean isGraphicsCore, String pciEdition, String pciConfiguration, int maximumPciPorts) {
        this.product = product;
        this.socket = socket;
        this.multiplier = multiplier;
        this.numberOfStreams = numberOfStreams;
        this.maximumPerformanceCore = maximumPerformanceCore;
        this.maximumEfficiencyCore = maximumEfficiencyCore;
        this.basePerformanceCore = basePerformanceCore;
        this.baseEfficiencyCore = baseEfficiencyCore;
        this.consumption = consumption;
        this.cache = cache;
        this.motherboardCompatible = motherboardCompatible;
        this.maximumSupportMemory = maximumSupportMemory;
        this.maximumBandwidth = maximumBandwidth;
        this.memoryType = memoryType;
        this.isGraphicsCore = isGraphicsCore;
        this.pciEdition = pciEdition;
        this.pciConfiguration = pciConfiguration;
        this.maximumPciPorts = maximumPciPorts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
