package vn.graybee.requests.products.detail;

import jakarta.validation.constraints.Size;
import vn.graybee.requests.products.DetailTemplateRequest;

public class PcDetailRequest extends DetailTemplateRequest {
    
    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String mainBoard;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String cpu;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String ram;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String vga;

    @Size(max = 50, message = "Độ dài tối đa 50 ký tự.")
    private String hdd;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String ssd;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String psu;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String caseName;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String cooling;

    private String ioPorts;

    @Size(max = 50, message = "Độ dài tối đa 50 ký tự.")
    private String os;

    @Size(max = 100, message = "Độ dài tối đa 100 ký tự.")
    private String connectivity;

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

}
