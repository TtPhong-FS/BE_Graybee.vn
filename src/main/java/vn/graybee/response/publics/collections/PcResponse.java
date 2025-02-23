package vn.graybee.response.publics.collections;

public class PcResponse {

    private String cpu;

    private String motherboard;

    private String ram;

    private String vga;

    private String ssd;

    public PcResponse(String cpu, String motherboard, String ram, String vga, String ssd) {
        this.cpu = cpu;
        this.motherboard = motherboard;
        this.ram = ram;
        this.vga = vga;
        this.ssd = ssd;
    }

    public String getSsd() {
        return ssd;
    }

    public void setSsd(String ssd) {
        this.ssd = ssd;
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

    public String getVga() {
        return vga;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }


}
