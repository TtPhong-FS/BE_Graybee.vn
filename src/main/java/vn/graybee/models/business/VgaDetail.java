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
@Table(name = "vga_details")
public class VgaDetail {

    @Id
    @Column(name = "product_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "memory_speed", nullable = false)
    private int memorySpeed;

    @Column(name = "memory_protocol", nullable = false)
    private int memoryProtocol;

    @Column(name = "maximum_resolution", length = 20)
    private String maximumResolution;

    @Column(name = "multiple_screen")
    private int multipleScreen;

    @Column(name = "protocols", length = 100, nullable = false)
    private String protocols;

    @Column(name = "gpu_clock", nullable = false)
    private int gpuClock;

    @Column(name = "bus_standard", length = 50, nullable = false)
    private String busStandard;

    @Column(name = "number_of_processing_unit", nullable = false)
    private int numberOfProcessingUnit;

    @Column(name = "power_consumption", nullable = false)
    private int powerConsumption;

    @Column(name = "psu_recommend", nullable = false)
    private int psuRecommend;

    @Column(name = "directx")
    private int directx;

    @Column(name = "is_application_support")
    private boolean isApplicationSupport;

    public VgaDetail() {
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

    public VgaDetail(Product product, int memorySpeed, int memoryProtocol, String maximumResolution, int multipleScreen, String protocols, int gpuClock, String busStandard, int numberOfProcessingUnit, int powerConsumption, int psuRecommend, int directx, boolean isApplicationSupport) {
        this.product = product;
        this.memorySpeed = memorySpeed;
        this.memoryProtocol = memoryProtocol;
        this.maximumResolution = maximumResolution;
        this.multipleScreen = multipleScreen;
        this.protocols = protocols;
        this.gpuClock = gpuClock;
        this.busStandard = busStandard;
        this.numberOfProcessingUnit = numberOfProcessingUnit;
        this.powerConsumption = powerConsumption;
        this.psuRecommend = psuRecommend;
        this.directx = directx;
        this.isApplicationSupport = isApplicationSupport;
    }

}
