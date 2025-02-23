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
@Table(name = "pc_details")
public class PcDetail {

    @Id
    @Column(name = "product_id")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @MapsId
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 100, nullable = false)
    private String demand;

    @Column(length = 200, nullable = false)
    private String cpu;

    @Column(length = 100, nullable = false)
    private String motherboard;

    @Column(length = 100, nullable = false)
    private String ram;

    @Column(length = 150, nullable = false)
    private String ssd;

    @Column(length = 100, nullable = false)
    private String hdd;

    @Column(name = "operating_system", length = 50, nullable = false)
    private String operatingSystem;

    @Column(length = 150, nullable = false)
    private String vga;

    @Column(name = "input_port", length = 100)
    private String inputPort;

    @Column(name = "output_port", length = 100)
    private String outputPort;

    @Column(length = 100)
    private String cooling;

    @Column(length = 150)
    private String psu;

    @Column(name = "case_name", length = 200)
    private String caseName;

    public PcDetail(Product product, String demand, String cpu, String motherboard, String ram, String ssd, String hdd, String operatingSystem, String vga, String inputPort, String outputPort, String cooling, String psu, String caseName) {
        this.product = product;
        this.demand = demand;
        this.cpu = cpu;
        this.motherboard = motherboard;
        this.ram = ram;
        this.ssd = ssd;
        this.hdd = hdd;
        this.operatingSystem = operatingSystem;
        this.vga = vga;
        this.inputPort = inputPort;
        this.outputPort = outputPort;
        this.cooling = cooling;
        this.psu = psu;
        this.caseName = caseName;
    }

    public PcDetail() {
    }

    public String getSsd() {
        return ssd;
    }

    public void setSsd(String ssd) {
        this.ssd = ssd;
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
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

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
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

    public String getInputPort() {
        return inputPort;
    }

    public void setInputPort(String inputPort) {
        this.inputPort = inputPort;
    }

    public String getOutputPort() {
        return outputPort;
    }

    public void setOutputPort(String outputPort) {
        this.outputPort = outputPort;
    }

    public String getCooling() {
        return cooling;
    }

    public void setCooling(String cooling) {
        this.cooling = cooling;
    }

    public String getPsu() {
        return psu;
    }

    public void setPsu(String psu) {
        this.psu = psu;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

}
