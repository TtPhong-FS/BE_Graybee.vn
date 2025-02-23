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

    @Column(name = "memory_support", length = 500, nullable = false)
    private String memorySupport;

    @Column(name = "integrated_graphics", length = 300)
    private String integratedGraphics;

    @Column(name = "sound_support", length = 300)
    private String soundSupport;

    @Column(name = "expansion_slots", length = 200)
    private String expansionSlots;

    @Column(name = "storage_support", length = 200, nullable = false)
    private String storageSupport;

    @Column(name = "usb_support", length = 300, nullable = false)
    private String usbSupport;

    @Column(name = "wireless_connectivity", length = 300)
    private String wirelessConnectivity;

    @Column(name = "operating_system_support", length = 150)
    private String operatingSystemSupport;

    @Column(name = "internal_input_output_connectivity", length = 600)
    private String internalInputOutputConnectivity;

    @Column(name = "rear_input_output_connectivity", length = 400)
    private String rearInputOutputConnectivity;

    @Column(name = "supporting_software", length = 300)
    private String supportingSoftware;

    @Column(length = 100)
    private String bios;

    @Column(length = 200)
    private String accessory;

    public MotherBoardDetail() {
    }

    public MotherBoardDetail(Product product, String chipset, String socket, String cpuSupport, String memorySupport, String integratedGraphics, String soundSupport, String expansionSlots, String storageSupport, String usbSupport, String wirelessConnectivity, String operatingSystemSupport, String internalInputOutputConnectivity, String rearInputOutputConnectivity, String supportingSoftware, String bios, String accessory) {
        this.product = product;
        this.chipset = chipset;
        this.socket = socket;
        this.cpuSupport = cpuSupport;
        this.memorySupport = memorySupport;
        this.integratedGraphics = integratedGraphics;
        this.soundSupport = soundSupport;
        this.expansionSlots = expansionSlots;
        this.storageSupport = storageSupport;
        this.usbSupport = usbSupport;
        this.wirelessConnectivity = wirelessConnectivity;
        this.operatingSystemSupport = operatingSystemSupport;
        this.internalInputOutputConnectivity = internalInputOutputConnectivity;
        this.rearInputOutputConnectivity = rearInputOutputConnectivity;
        this.supportingSoftware = supportingSoftware;
        this.bios = bios;
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

    public String getMemorySupport() {
        return memorySupport;
    }

    public void setMemorySupport(String memorySupport) {
        this.memorySupport = memorySupport;
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

    public String getRearInputOutputConnectivity() {
        return rearInputOutputConnectivity;
    }

    public void setRearInputOutputConnectivity(String rearInputOutputConnectivity) {
        this.rearInputOutputConnectivity = rearInputOutputConnectivity;
    }

    public String getSupportingSoftware() {
        return supportingSoftware;
    }

    public void setSupportingSoftware(String supportingSoftware) {
        this.supportingSoftware = supportingSoftware;
    }

    public String getBios() {
        return bios;
    }

    public void setBios(String bios) {
        this.bios = bios;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

}
