package vn.graybee.response.admin.directories.manufacturer;

import vn.graybee.enums.DirectoryStatus;
import vn.graybee.models.directories.Manufacturer;
import vn.graybee.response.BaseResponse;

public class ManufacturerResponse extends BaseResponse {

    private int id;

    private String name;

    private DirectoryStatus status;

    private int productCount;

    public ManufacturerResponse(Manufacturer manufacturer) {
        super(manufacturer.getCreatedAt(), manufacturer.getUpdatedAt());
        this.id = manufacturer.getId();
        this.name = manufacturer.getName();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DirectoryStatus getStatus() {
        return status;
    }

    public void setStatus(DirectoryStatus status) {
        this.status = status;
    }

}
