package vn.graybee.requests.carousels;

public class CarouselItemCreateRequest {

    private int carouselGroupId;

    private long productId;

    private int position;

    private boolean active;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCarouselGroupId() {
        return carouselGroupId;
    }

    public void setCarouselGroupId(int carouselGroupId) {
        this.carouselGroupId = carouselGroupId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
