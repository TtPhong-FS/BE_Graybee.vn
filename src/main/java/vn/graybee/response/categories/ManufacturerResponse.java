package vn.graybee.response.categories;

import vn.graybee.response.BaseResponse;

import java.time.LocalDateTime;

public class ManufacturerResponse extends BaseResponse {

    private int id;

    private String manufacturerName;

    private String status;

    private int productCount;

    public ManufacturerResponse(LocalDateTime createdAt, LocalDateTime updatedAt, int id, String manufacturerName, String status, int productCount) {
        super(createdAt, updatedAt);
        this.id = id;
        this.manufacturerName = manufacturerName;
        this.status = status;
        this.productCount = productCount;
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
