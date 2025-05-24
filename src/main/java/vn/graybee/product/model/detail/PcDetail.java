package vn.graybee.product.model.detail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pc_detail")
public class PcDetail {

    @Column(name = "main_board", length = 100)
    private String mainBoard;

    @Column(length = 100)
    private String cpu;

    @Column(length = 100)
    private String ram;

    @Column(length = 100)
    private String vga;

    @Column(length = 50)
    private String hdd;

    @Column(length = 100)
    private String ssd;

    @Column(length = 100)
    private String psu;

    @Column(name = "case_name", length = 100)
    private String caseName;

    @Column(length = 100)
    private String cooling;

    private String ioPorts;

    @Column(length = 50)
    private String os;

    @Column(length = 100)
    private String connectivity;

    @Id
    @Column(name = "product_id", nullable = false)
    private Long productId;

    public PcDetail() {
    }

    public String getMainBoard() {
        return mainBoard;
    }

    public void setMainBoard(String mainBoard) {
        this.mainBoard = mainBoard;
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

    public String getVga() {
        return vga;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public String getSsd() {
        return ssd;
    }

    public void setSsd(String ssd) {
        this.ssd = ssd;
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

    public String getCooling() {
        return cooling;
    }

    public void setCooling(String cooling) {
        this.cooling = cooling;
    }

    public String getIoPorts() {
        return ioPorts;
    }

    public void setIoPorts(String ioPorts) {
        this.ioPorts = ioPorts;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(String connectivity) {
        this.connectivity = connectivity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

}
