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
@Table(name = "vga_details")
public class VgaDetail {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "memory_speed", nullable = false)
    private int memorySpeed;

    @Column(length = 30, nullable = false)
    private String memory;

    @Column(name = "memory_bus", nullable = false)
    private int memoryBus;

    @Column(name = "cuda_kernel", nullable = false)
    private int cudaKernel;

    @Column(name = "maximum_resolution", length = 30, nullable = false)
    private String maximumResolution;

    @Column(name = "maximum_screen")
    private int maximumScreen;

    @Column(length = 200, nullable = false)
    private String ports;

    @Column(nullable = false, length = 200)
    private String clock;

    @Column(name = "power_consumption", nullable = false)
    private int powerConsumption;

    @Column(name = "psu_recommend", nullable = false)
    private int psuRecommend;

    @Column(name = "is_application_support")
    private boolean isApplicationSupport;

    public VgaDetail() {
    }

    public VgaDetail(Long productId, int memorySpeed, String memory, int memoryBus, int cudaKernel, String maximumResolution, int maximumScreen, String ports, String clock, int powerConsumption, int psuRecommend, boolean isApplicationSupport) {
        this.productId = productId;
        this.memorySpeed = memorySpeed;
        this.memory = memory;
        this.memoryBus = memoryBus;
        this.cudaKernel = cudaKernel;
        this.maximumResolution = maximumResolution;
        this.maximumScreen = maximumScreen;
        this.ports = ports;
        this.clock = clock;
        this.powerConsumption = powerConsumption;
        this.psuRecommend = psuRecommend;
        this.isApplicationSupport = isApplicationSupport;
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

    public int getMemorySpeed() {
        return memorySpeed;
    }

    public void setMemorySpeed(int memorySpeed) {
        this.memorySpeed = memorySpeed;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public int getMemoryBus() {
        return memoryBus;
    }

    public void setMemoryBus(int memoryBus) {
        this.memoryBus = memoryBus;
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
