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
@Table(name = "monitor_details")
public class MonitorDetail {


    @Id
    @Column(name = "product_id")
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "screen_size", nullable = false)
    private int screenSize;

    @Column(name = "screen_type", length = 20, nullable = false)
    private String screenType;

    @Column(nullable = false, length = 30)
    private String panels;

    @Column(name = "aspect_ratio", length = 10, nullable = false)
    private String aspectRatio;

    @Column(name = "is_speaker")
    private boolean isSpeaker;

    @Column(length = 40, nullable = false)
    private String resolution;

    @Column(name = "color_display", nullable = false)
    private int colorDisplay;

    @Column(name = "percent_color", nullable = false)
    private int percentColor;

    @Column(name = "refresh_rate")
    private int refreshRate;

    @Column(length = 50, nullable = false)
    private String ports;

    @Column(name = "power_consumption")
    private int powerConsumption;

    @Column(name = "power_save_mode")
    private int powerSaveMode;

    @Column(name = "power_off_mode")
    private float powerOffMode;

    private float voltage;

    @Column(name = "special_feature", length = 50)
    private String specialFeature;

    @Column(name = "life_span")
    private int lifeSpan;

    @Column(length = 100)
    private String accessory;

    public MonitorDetail(Long productId, int screenSize, String screenType, String panels, String aspectRatio, boolean isSpeaker, String resolution, int colorDisplay, int percentColor, int refreshRate, String ports, int powerConsumption, int powerSaveMode, float powerOffMode, float voltage, String specialFeature, int lifeSpan, String accessory) {
        this.productId = productId;
        this.screenSize = screenSize;
        this.screenType = screenType;
        this.panels = panels;
        this.aspectRatio = aspectRatio;
        this.isSpeaker = isSpeaker;
        this.resolution = resolution;
        this.colorDisplay = colorDisplay;
        this.percentColor = percentColor;
        this.refreshRate = refreshRate;
        this.ports = ports;
        this.powerConsumption = powerConsumption;
        this.powerSaveMode = powerSaveMode;
        this.powerOffMode = powerOffMode;
        this.voltage = voltage;
        this.specialFeature = specialFeature;
        this.lifeSpan = lifeSpan;
        this.accessory = accessory;
    }

    public MonitorDetail() {
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

    public int getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(int screenSize) {
        this.screenSize = screenSize;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getPanels() {
        return panels;
    }

    public void setPanels(String panels) {
        this.panels = panels;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public boolean isSpeaker() {
        return isSpeaker;
    }

    public void setSpeaker(boolean speaker) {
        isSpeaker = speaker;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getColorDisplay() {
        return colorDisplay;
    }

    public void setColorDisplay(int colorDisplay) {
        this.colorDisplay = colorDisplay;
    }

    public int getPercentColor() {
        return percentColor;
    }

    public void setPercentColor(int percentColor) {
        this.percentColor = percentColor;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public int getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(int powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public int getPowerSaveMode() {
        return powerSaveMode;
    }

    public void setPowerSaveMode(int powerSaveMode) {
        this.powerSaveMode = powerSaveMode;
    }

    public float getPowerOffMode() {
        return powerOffMode;
    }

    public void setPowerOffMode(float powerOffMode) {
        this.powerOffMode = powerOffMode;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public String getSpecialFeature() {
        return specialFeature;
    }

    public void setSpecialFeature(String specialFeature) {
        this.specialFeature = specialFeature;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getAccessory() {
        return accessory;
    }

    public void setAccessory(String accessory) {
        this.accessory = accessory;
    }

}
