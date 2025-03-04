package vn.graybee.response.categories;

import vn.graybee.response.BaseResponse;

import java.time.LocalDateTime;

public class ManufacturerResponse extends BaseResponse {

    private int id;

    private String name;

    private String status;

    private int productCount;

    public ManufacturerResponse(LocalDateTime createdAt, LocalDateTime updatedAt, int id, String name, String status, int productCount) {
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
