package vn.graybee.response.categories;

import vn.graybee.response.BaseResponse;

import java.time.LocalDateTime;

public class CategoryResponse extends BaseResponse {

    private int id;

    private String name;

    private boolean isDeleted;

    public CategoryResponse(LocalDateTime createdAt, LocalDateTime updatedAt, int id, String name, boolean isDeleted) {
        super(createdAt, updatedAt);
        this.id = id;
        this.name = name;
        this.isDeleted = isDeleted;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean delete) {
        isDeleted = delete;
    }

}
