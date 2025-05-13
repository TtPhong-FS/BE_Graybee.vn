package vn.graybee.response.publics.products.detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import vn.graybee.models.products.detail.PcDetail;
import vn.graybee.response.publics.products.DetailTemplateResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PcDetailResponse extends DetailTemplateResponse {

    private String mainBoard;

    private String cpu;

    private String ram;

    private String vga;

    private String hdd;

    private String ssd;

    private String psu;

    private String caseName;

    private String cooling;

    private String ioPorts;

    private String os;

    private String connectivity;

    public PcDetailResponse() {
    }

    public PcDetailResponse(PcDetail pcDetail) {
        this.mainBoard = pcDetail.getMainBoard();
        this.cpu = pcDetail.getCpu();
        this.ram = pcDetail.getRam();
        this.vga = pcDetail.getVga();
        this.hdd = pcDetail.getHdd();
        this.ssd = pcDetail.getSsd();
        this.psu = pcDetail.getPsu();
        this.caseName = pcDetail.getCaseName();
        this.cooling = pcDetail.getCooling();
        this.ioPorts = pcDetail.getIoPorts();
        this.os = pcDetail.getOs();
        this.connectivity = pcDetail.getConnectivity();
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

    @Override
    public String getDetailType() {
        return "pc";
    }

}
