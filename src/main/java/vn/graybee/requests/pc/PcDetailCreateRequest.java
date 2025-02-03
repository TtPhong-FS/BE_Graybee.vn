package vn.graybee.requests.pc;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import vn.graybee.requests.ProductCreateRequest;

public class PcDetailCreateRequest extends ProductCreateRequest {

    @Size(min = 1, max = 30, message = "Must be between 1 and 30 characters")
    @NotBlank(message = "Cannot be blank")
    private String demand;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @NotBlank(message = "Cannot be blank")
    private String cpu;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    private String motherboard;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @NotBlank(message = "Cannot be blank")
    private String ram;

    @Size(min = 1, max = 200, message = "Must be between 1 and 200 characters")
    @NotBlank(message = "Cannot be blank")
    private String storage;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")

    @NotBlank(message = "Cannot be blank")
    @JsonProperty("operating_system")
    private String operatingSystem;

    @Size(min = 1, max = 150, message = "Must be between 1 and 150 characters")
    @NotBlank(message = "Cannot be blank")
    private String vga;

    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("input_port")
    private String inputPort;

    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    @NotBlank(message = "Cannot be blank")
    @JsonProperty("output_port")
    private String outputPort;

    @Size(min = 1, max = 100, message = "Must be between 1 and 100 characters")
    @NotBlank(message = "Cannot be blank")
    private String cooling;

    @Size(min = 1, max = 150, message = "Must be between 1 and 150 characters")
    @NotBlank(message = "Cannot be blank")
    private String psu;

    @Size(min = 1, max = 50, message = "Must be between 1 and 50 characters")
    @JsonProperty("case_name")
    private String caseName = "unknown";

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

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
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
