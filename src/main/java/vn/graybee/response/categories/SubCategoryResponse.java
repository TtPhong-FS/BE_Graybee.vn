package vn.graybee.response.categories;

import vn.graybee.response.BaseResponse;

import java.time.LocalDateTime;

public class SubCategoryResponse extends BaseResponse {

    private int id;

    private String subcategoryName;

    private String status;

    public SubCategoryResponse(LocalDateTime createdAt, LocalDateTime updatedAt, int id, String subcategoryName, String status) {
        super(createdAt, updatedAt);
        this.id = id;
        this.subcategoryName = subcategoryName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
