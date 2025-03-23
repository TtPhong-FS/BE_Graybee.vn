package vn.graybee.response.admin.directories.category;

import vn.graybee.enums.CategoryStatus;

public class CategoryStatusResponse {

    private int id;

    private CategoryStatus status;

    public CategoryStatusResponse(int id, CategoryStatus status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

}
