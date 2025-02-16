package vn.graybee.response.publics.pc;

import vn.graybee.projections.publics.PcSummaryProjection;
import vn.graybee.utils.HardwareExtractor;

public class PcSummaryResponse {

    private String cpu;

    private String motherboard;

    private String ram;

    private String vga;

    private String storage;

    public PcSummaryResponse(PcSummaryProjection product, HardwareExtractor extractor) {
        this.cpu = extractor.extract(product.getCpu(), "cpu");
        this.motherboard = extractor.extract(product.getMotherboard(), "motherboard");
        this.ram = extractor.extract(product.getRam(), "ram");
        this.vga = extractor.extract(product.getVga(), "vga");
        this.storage = extractor.extract(product.getStorage(), "storage");
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
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
