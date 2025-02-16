package vn.graybee.models.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import vn.graybee.models.products.Product;

@Entity
@Table(name = "motherboard_details")
public class MotherBoardDetail {

    @Id
    @Column(name = "product_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 30, nullable = false)
    private String chipset;

    @Column(length = 30, nullable = false)
    private String socket;

    @Column(name = "cpu_support", length = 200, nullable = false)
    private String cpuSupport;

    @Column(name = "technology_support", length = 80)
    private String technologySupport;

    @Column(name = "memory_support", length = 500, nullable = false)
    private String memorySupport;

    @Column(name = "slot_memory_support", length = 50, nullable = false)
    private String slotMemorySupport;

    @Column(name = "maximum_memory_support", nullable = false)
    private int maximumMemorySupport;

    @Column(name = "integrated_graphics", length = 500, nullable = false)
    private String integratedGraphics;

    @Column(name = "sound_support", length = 800, nullable = false)
    private String soundSupport;

    @Column(name = "lan_support", length = 100, nullable = false)
    private String lanSupport;

    @Column(name = "expansion_slots", length = 200, nullable = false)
    private String expansionSlots;

    @Column(name = "storage_support", length = 200, nullable = false)
    private String storageSupport;

    @Column(name = "usb_support", length = 300, nullable = false)
    private String usbSupport;

    @Column(name = "wireless_connectivity", length = 200)
    private String wirelessConnectivity;

    @Column(name = "operating_system_support", length = 50, nullable = false)
    private String operatingSystemSupport;

    @Column(name = "internal_input_output_connectivity", length = 500, nullable = false)
    private String internalInputOutputConnectivity;

    @Column(name = "internal_input_connectivity", length = 800, nullable = false)
    private String internalInputConnectivity;

    @Column(name = "internal_output_connectivity", length = 800, nullable = false)
    private String internalOutputConnectivity;

    @Column(name = "rear_input_connectivity", length = 200, nullable = false)
    private String rearInputConnectivity;

    @Column(name = "rear_output_connectivity", length = 200, nullable = false)
    private String rearOutputConnectivity;

    @Column(name = "rear_input_output_connectivity", length = 200, nullable = false)
    private String rearInputOutputConnectivity;

    @Column(name = "system_monitoring_application", length = 300)
    private String systemMonitoringApplication;

    @Column(length = 100)
    private String bios;

    @Column(name = "special_features", length = 1000)
    private String specialFeatures;

    @Column(name = "unique_features", length = 1000)
    private String uniqueFeatures;

    @Column(length = 300)
    private String accessory;

    public MotherBoardDetail() {
    }

    public MotherBoardDetail(Product product, String chipset, String socket, String cpuSupport, String technologySupport, String memorySupport, String slotMemorySupport, int maximumMemorySupport, String integratedGraphics, String soundSupport, String lanSupport, String expansionSlots, String storageSupport, String usbSupport, String wirelessConnectivity, String operatingSystemSupport, String internalInputOutputConnectivity, String internalInputConnectivity, String internalOutputConnectivity, String rearInputConnectivity, String rearOutputConnectivity, String rearInputOutputConnectivity, String systemMonitoringApplication, String bios, String specialFeatures, String uniqueFeatures, String accessory) {
        this.product = product;
        this.chipset = chipset;
        this.socket = socket;
        this.cpuSupport = cpuSupport;
        this.technologySupport = technologySupport;
        this.memorySupport = memorySupport;
        this.slotMemorySupport = slotMemorySupport;
        this.maximumMemorySupport = maximumMemorySupport;
        this.integratedGraphics = integratedGraphics;
        this.soundSupport = soundSupport;
        this.lanSupport = lanSupport;
        this.expansionSlots = expansionSlots;
        this.storageSupport = storageSupport;
        this.usbSupport = usbSupport;
        this.wirelessConnectivity = wirelessConnectivity;
        this.operatingSystemSupport = operatingSystemSupport;
        this.internalInputOutputConnectivity = internalInputOutputConnectivity;
        this.internalInputConnectivity = internalInputConnectivity;
        this.internalOutputConnectivity = internalOutputConnectivity;
        this.rearInputConnectivity = rearInputConnectivity;
        this.rearOutputConnectivity = rearOutputConnectivity;
        this.rearInputOutputConnectivity = rearInputOutputConnectivity;
        this.systemMonitoringApplication = systemMonitoringApplication;
        this.bios = bios;
        this.specialFeatures = specialFeatures;
        this.uniqueFeatures = uniqueFeatures;
        this.accessory = accessory;
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

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getCpuSupport() {
        return cpuSupport;
    }

    public void setCpuSupport(String cpuSupport) {
        this.cpuSupport = cpuSupport;
    }

    public String getTechnologySupport() {
        return technologySupport;
    }

    public void setTechnologySupport(String technologySupport) {
        this.technologySupport = technologySupport;
    }

    public String getMemorySupport() {
        return memorySupport;
    }

    public void setMemorySupport(String memorySupport) {
        this.memorySupport = memorySupport;
    }

    public String getSlotMemorySupport() {
        return slotMemorySupport;
    }

    public void setSlotMemorySupport(String slotMemorySupport) {
        this.slotMemorySupport = slotMemorySupport;
    }

    public int getMaximumMemorySupport() {
        return maximumMemorySupport;
    }

    public void setMaximumMemorySupport(int maximumMemorySupport) {
        this.maximumMemorySupport = maximumMemorySupport;
    }

    public String getIntegratedGraphics() {
        return integratedGraphics;
    }

    public void setIntegratedGraphics(String integratedGraphics) {
        this.integratedGraphics = integratedGraphics;
    }

    public String getSoundSupport() {
        return soundSupport;
    }

    public void setSoundSupport(String soundSupport) {
        this.soundSupport = soundSupport;
    }

    public String getLanSupport() {
        return lanSupport;
    }

    public void setLanSupport(String lanSupport) {
        this.lanSupport = lanSupport;
    }

    public String getExpansionSlots() {
        return expansionSlots;
    }

    public void setExpansionSlots(String expansionSlots) {
        this.expansionSlots = expansionSlots;
    }

    public String getStorageSupport() {
        return storageSupport;
    }

    public void setStorageSupport(String storageSupport) {
        this.storageSupport = storageSupport;
    }

    public String getUsbSupport() {
        return usbSupport;
    }

    public void setUsbSupport(String usbSupport) {
        this.usbSupport = usbSupport;
    }

    public String getWirelessConnectivity() {
        return wirelessConnectivity;
    }

    public void setWirelessConnectivity(String wirelessConnectivity) {
        this.wirelessConnectivity = wirelessConnectivity;
    }

    public String getOperatingSystemSupport() {
        return operatingSystemSupport;
    }

    public void setOperatingSystemSupport(String operatingSystemSupport) {
        this.operatingSystemSupport = operatingSystemSupport;
    }

    public String getInternalInputOutputConnectivity() {
        return internalInputOutputConnectivity;
    }

    public void setInternalInputOutputConnectivity(String internalInputOutputConnectivity) {
        this.internalInputOutputConnectivity = internalInputOutputConnectivity;
    }

    public String getInternalInputConnectivity() {
        return internalInputConnectivity;
    }

    public void setInternalInputConnectivity(String internalInputConnectivity) {
        this.internalInputConnectivity = internalInputConnectivity;
    }

    public String getInternalOutputConnectivity() {
        return internalOutputConnectivity;
    }

    public void setInternalOutputConnectivity(String internalOutputConnectivity) {
        this.internalOutputConnectivity = internalOutputConnectivity;
    }

    public String getRearInputConnectivity() {
        return rearInputConnectivity;
    }

    public void setRearInputConnectivity(String rearInputConnectivity) {
        this.rearInputConnectivity = rearInputConnectivity;
    }

    public String getRearOutputConnectivity() {
        return rearOutputConnectivity;
    }

    public void setRearOutputConnectivity(String rearOutputConnectivity) {
        this.rearOutputConnectivity = rearOutputConnectivity;
    }

    public String getRearInputOutputConnectivity() {
        return rearInputOutputConnectivity;
    }

    public void setRearInputOutputConnectivity(String rearInputOutputConnectivity) {
        this.rearInputOutputConnectivity = rearInputOutputConnectivity;
    }

    public String getSystemMonitoringApplication() {
        return systemMonitoringApplication;
    }

    public void setSystemMonitoringApplication(String systemMonitoringApplication) {
        this.systemMonitoringApplication = systemMonitoringApplication;
    }

    public String getBios() {
        return bios;
    }

    public void setBios(String bios) {
        this.bios = bios;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public String getUniqueFeatures() {
        return uniqueFeatures;
    }

    public void setUniqueFeatures(String uniqueFeatures) {
        this.uniqueFeatures = uniqueFeatures;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

}
