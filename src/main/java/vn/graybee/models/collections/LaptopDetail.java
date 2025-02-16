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
@Table(name = "laptop_details")
public class LaptopDetail {

    @Id
    @Column(name = "product_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 200, nullable = false)
    private String cpu;

    @Column(length = 100, nullable = false)
    private String ram;

    @Column(length = 100, nullable = false)
    private String demand;

    @Column(length = 200, nullable = false)
    private String storage;

    @Column(name = "operating_system", length = 50, nullable = false)
    private String operatingSystem;

    @Column(length = 100, nullable = false)
    private String vga;

    @Column(length = 200, nullable = false)
    private String monitor;

    @Column(length = 200, nullable = false)
    private String ports;

    @Column(length = 100, nullable = false)
    private String keyboard;

    @Column(name = "wireless_connectivity", length = 100, nullable = false)
    private String wirelessConnectivity;

    @Column(length = 100, nullable = false)
    private String audio;

    @Column(length = 100, nullable = false)
    private String webcam;

    @Column(nullable = false)
    private int battery;

    @Column(length = 50)
    private String material;

    @Column(length = 100)
    private String confidentiality;

    public LaptopDetail(Product product, String cpu, String ram, String demand, String storage, String operatingSystem, String vga, String monitor, String ports, String keyboard, String wirelessConnectivity, String audio, String webcam, int battery, String material, String confidentiality) {
        this.product = product;
        this.cpu = cpu;
        this.ram = ram;
        this.demand = demand;
        this.storage = storage;
        this.operatingSystem = operatingSystem;
        this.vga = vga;
        this.monitor = monitor;
        this.ports = ports;
        this.keyboard = keyboard;
        this.wirelessConnectivity = wirelessConnectivity;
        this.audio = audio;
        this.webcam = webcam;
        this.battery = battery;
        this.material = material;
        this.confidentiality = confidentiality;
    }

    public LaptopDetail() {
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

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getVga() {
        return vga;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getWirelessConnectivity() {
        return wirelessConnectivity;
    }

    public void setWirelessConnectivity(String wirelessConnectivity) {
        this.wirelessConnectivity = wirelessConnectivity;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getWebcam() {
        return webcam;
    }

    public void setWebcam(String webcam) {
        this.webcam = webcam;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getConfidentiality() {
        return confidentiality;
    }

    public void setConfidentiality(String confidentiality) {
        this.confidentiality = confidentiality;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

}
