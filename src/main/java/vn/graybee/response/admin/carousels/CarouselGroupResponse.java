package vn.graybee.response.admin.carousels;

import vn.graybee.response.BaseResponse;

import java.time.LocalDateTime;

public class CarouselGroupResponse extends BaseResponse {

    private int id;

    private String type;

    private String name;

    private String categoryName;

    private boolean active;

    public CarouselGroupResponse(LocalDateTime createdAt, LocalDateTime updatedAt, int id, String type, String name, String categoryName, boolean active) {
        super(createdAt, updatedAt);
        this.id = id;
        this.type = type;
        this.name = name;
        this.categoryName = categoryName;
        this.active = active;
    }

    public CarouselGroupResponse(LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
