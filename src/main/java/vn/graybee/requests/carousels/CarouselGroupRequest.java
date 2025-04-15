package vn.graybee.requests.carousels;

import jakarta.validation.constraints.NotBlank;

public class CarouselGroupRequest {

    @NotBlank(message = "Vui lòng nhập tên cho Carousel")
    private String name;

    @NotBlank(message = "Vui lòng chọn loại cho Carousel")
    private String type;

    private Integer categoryId;

    private boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
