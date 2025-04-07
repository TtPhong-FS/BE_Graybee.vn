//package vn.graybee.response.publics.collections;
//
//import vn.graybee.response.publics.products.ProductBasicResponse;
//import vn.graybee.utils.HardwareExtractor;
//
//public class PcSummaryResponse extends ProductBasicResponse {
//
//    private String cpu;
//
//    private String motherboard;
//
//    private String ram;
//
//    private String vga;
//
//    private String ssd;
//
//    public PcSummaryResponse(long id, String name, float finalPrice, float price, String thumbnail, PcResponse product, HardwareExtractor extractor) {
//        super(id, name, price, finalPrice, thumbnail);
//        this.cpu = extractor.extract(product.getCpu(), "cpu");
//        this.motherboard = extractor.extract(product.getMotherboard(), "motherboard");
//        this.ram = extractor.extract(product.getRam(), "ram");
//        this.vga = extractor.extract(product.getVga(), "vga");
//        this.ssd = extractor.extract(product.getSsd(), "ssd");
//    }
//
//
//    public String getCpu() {
//        return cpu;
//    }
//
//    public void setCpu(String cpu) {
//        this.cpu = cpu;
//    }
//
//    public String getMotherboard() {
//        return motherboard;
//    }
//
//    public void setMotherboard(String motherboard) {
//        this.motherboard = motherboard;
//    }
//
//    public String getRam() {
//        return ram;
//    }
//
//    public void setRam(String ram) {
//        this.ram = ram;
//    }
//
//    public String getVga() {
//        return vga;
//    }
//
//    public void setVga(String vga) {
//        this.vga = vga;
//    }
//
//    public String getSsd() {
//        return ssd;
//    }
//
//    public void setSsd(String ssd) {
//        this.ssd = ssd;
//    }
//
//}
