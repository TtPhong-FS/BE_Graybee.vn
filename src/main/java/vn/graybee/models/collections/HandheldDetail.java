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
@Table(name = "handheld_details")
public class HandheldDetail {

    @Id
    @Column(name = "product_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "connect_mode", nullable = false, length = 50)
    private String connectMode;

    @Column(name = "number_of_nodes", nullable = false)
    private int numberOfNodes;

    @Column(length = 50)
    private String support;

    @Column(name = "usage_time", nullable = false)
    private int usageTime;

    @Column(length = 40, nullable = false)
    private String battery;

    @Column(length = 50)
    private String charging;

    @Column(length = 30)
    private String led;

    public HandheldDetail() {
    }

    public HandheldDetail(Product product, String connectMode, int numberOfNodes, String support, int usageTime, String battery, String charging, String led) {
        this.product = product;
        this.connectMode = connectMode;
        this.numberOfNodes = numberOfNodes;
        this.support = support;
        this.usageTime = usageTime;
        this.battery = battery;
        this.charging = charging;
        this.led = led;
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

    public String getConnectMode() {
        return connectMode;
    }

    public void setConnectMode(String connectMode) {
        this.connectMode = connectMode;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public int getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(int usageTime) {
        this.usageTime = usageTime;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getCharging() {
        return charging;
    }

    public void setCharging(String charging) {
        this.charging = charging;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

}
