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
@Table(name = "mouse_details")
public class MouseDetail {


    @Id
    @Column(name = "product_id")
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 50)
    private String sensors;

    @Column(name = "number_of_nodes", nullable = false)
    private int numberOfNodes;

    @Column(name = "switch_type", length = 30)
    private String switchType;

    @Column(name = "switch_life", length = 30, nullable = false)
    private String switchLife;

    @Column(name = "polling_rate", nullable = false)
    private int pollingRate;

    @Column(length = 100)
    private String software;

    @Column(length = 40, nullable = false)
    private String connect;

    @Column(name = "is_wireless_connect")
    private boolean isWirelessConnect;

    @Column(length = 50)
    private String battery;

    @Column(length = 30)
    private String led;

    public MouseDetail(Long productId, String sensors, int numberOfNodes, String switchType, String switchLife, int pollingRate, String software, String connect, boolean isWirelessConnect, String battery, String led) {
        this.productId = productId;
        this.sensors = sensors;
        this.numberOfNodes = numberOfNodes;
        this.switchType = switchType;
        this.switchLife = switchLife;
        this.pollingRate = pollingRate;
        this.software = software;
        this.connect = connect;
        this.isWirelessConnect = isWirelessConnect;
        this.battery = battery;
        this.led = led;
    }

    public MouseDetail() {
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

    public String getSensors() {
        return sensors;
    }

    public void setSensors(String sensors) {
        this.sensors = sensors;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }

    public String getSwitchType() {
        return switchType;
    }

    public void setSwitchType(String switchType) {
        this.switchType = switchType;
    }

    public String getSwitchLife() {
        return switchLife;
    }

    public void setSwitchLife(String switchLife) {
        this.switchLife = switchLife;
    }

    public int getPollingRate() {
        return pollingRate;
    }

    public void setPollingRate(int pollingRate) {
        this.pollingRate = pollingRate;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public boolean isWirelessConnect() {
        return isWirelessConnect;
    }

    public void setWirelessConnect(boolean wirelessConnect) {
        isWirelessConnect = wirelessConnect;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

}
