package vn.graybee.response.categories;

import vn.graybee.enums.CategoryStatus;
import vn.graybee.response.BaseResponse;

import java.time.LocalDateTime;

public class SubCategoryResponse extends BaseResponse {

    private int id;

    private String name;

    private CategoryStatus status;

    public SubCategoryResponse(LocalDateTime createdAt, LocalDateTime updatedAt, int id, String name, CategoryStatus status) {
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.status = status;
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

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

}
