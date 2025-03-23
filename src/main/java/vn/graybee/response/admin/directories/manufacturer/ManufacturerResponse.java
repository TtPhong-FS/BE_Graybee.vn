package vn.graybee.response.admin.directories.manufacturer;

import vn.graybee.models.categories.Manufacturer;
import vn.graybee.response.BaseResponse;

public class ManufacturerResponse extends BaseResponse {

    private int id;

    private String manufacturerName;

    private String status;

    private int productCount;

    public ManufacturerResponse(Manufacturer manufacturer) {
        super(manufacturer.getCreatedAt(), manufacturer.getUpdatedAt());
        this.id = manufacturer.getId();
        this.manufacturerName = manufacturer.getManufacturerName();
        this.status = manufacturer.getStatus();
        this.productCount = manufacturer.getProductCount();
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
