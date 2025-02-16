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
@Table(name = "heat_dissipation_details")
public class HeatDissipationDetail {

    @Id
    @Column(name = "product_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "cpu_socket", length = 50, nullable = false)
    private String cpuSocket;

    @Column(length = 50, nullable = false)
    private String series;

    @Column(name = "heat_sink_material", length = 40)
    private String heatSinkMaterial;

    @Column(nullable = false)
    private int speed;

    private float airflow;

    @Column(name = "air_pressure")
    private float airPressure;

    @Column(name = "noise_level")
    private int noiseLevel;

    @Column(name = "is_application_control")
    private boolean isApplicationControl;

    @Column(length = 30)
    private String led;

    public HeatDissipationDetail(Product product, String cpuSocket, String series, String heatSinkMaterial, int speed, float airflow, float airPressure, int noiseLevel, boolean isApplicationControl, String led) {
        this.product = product;
        this.cpuSocket = cpuSocket;
        this.series = series;
        this.heatSinkMaterial = heatSinkMaterial;
        this.speed = speed;
        this.airflow = airflow;
        this.airPressure = airPressure;
        this.noiseLevel = noiseLevel;
        this.isApplicationControl = isApplicationControl;
        this.led = led;
    }

    public HeatDissipationDetail() {
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

    public String getCpuSocket() {
        return cpuSocket;
    }

    public void setCpuSocket(String cpuSocket) {
        this.cpuSocket = cpuSocket;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getHeatSinkMaterial() {
        return heatSinkMaterial;
    }

    public void setHeatSinkMaterial(String heatSinkMaterial) {
        this.heatSinkMaterial = heatSinkMaterial;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getAirflow() {
        return airflow;
    }

    public void setAirflow(float airflow) {
        this.airflow = airflow;
    }

    public float getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(float airPressure) {
        this.airPressure = airPressure;
    }

    public int getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(int noiseLevel) {
        this.noiseLevel = noiseLevel;
    }

    public boolean isApplicationControl() {
        return isApplicationControl;
    }

    public void setApplicationControl(boolean applicationControl) {
        isApplicationControl = applicationControl;
    }

    public String getLed() {
        return led;
    }

    public void setLed(String led) {
        this.led = led;
    }

}
