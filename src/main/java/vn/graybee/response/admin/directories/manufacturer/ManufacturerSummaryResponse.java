package vn.graybee.response.admin.directories.manufacturer;

public class ManufacturerSummaryResponse {

    private int id;

    private String manufacturerName;


    public ManufacturerSummaryResponse(int id, String manufacturerName) {
        this.id = id;
        this.manufacturerName = manufacturerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

}
