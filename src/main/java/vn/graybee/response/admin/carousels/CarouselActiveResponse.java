package vn.graybee.response.admin.carousels;

public class CarouselActiveResponse {

    private int id;

    private boolean active;

    public CarouselActiveResponse(int id, boolean active) {
        this.id = id;
        this.active = active;
    }

    public CarouselActiveResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
