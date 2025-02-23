package vn.graybee.response.collections;

import vn.graybee.models.collections.RamDetail;
import vn.graybee.response.DetailDtoResponse;

public class RamDetailDtoResponse extends DetailDtoResponse {

    private long productId;

    private String suitableFor;

    private String series;

    private int capacity;

    private String type;

    private int speed;

    private String latency;

    private float voltage;

    private boolean ecc;

    private boolean isHeatDissipation;

    private String led;

    public RamDetailDtoResponse(RamDetail ramDetail) {
        this.productId = ramDetail.getId();
        this.suitableFor = ramDetail.getSuitableFor();
        this.series = ramDetail.getSeries();
        this.capacity = ramDetail.getCapacity();
        this.type = ramDetail.getType();
        this.speed = ramDetail.getSpeed();
        this.latency = ramDetail.getLatency();
        this.voltage = ramDetail.getVoltage();
        this.ecc = ramDetail.isEcc();
        this.isHeatDissipation = ramDetail.isHeatDissipation();
        this.led = ramDetail.getLed();
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getSuitableFor() {
        return suitableFor;
    }

    public void setSuitableFor(String suitableFor) {
        this.suitableFor = suitableFor;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    public float getVoltage() {
        return voltage;
    }

    public void setVoltage(float voltage) {
        this.voltage = voltage;
    }

    public boolean isEcc() {
        return ecc;
    }

    public void setEcc(boolean ecc) {
        this.ecc = ecc;
    }

    public boolean isHeatDissipation() {
        return isHeatDissipation;
    }

    public void setHeatDissipation(boolean heatDissipation) {
        isHeatDissipation = heatDissipation;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

}
