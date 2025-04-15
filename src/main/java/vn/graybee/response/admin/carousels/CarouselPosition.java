package vn.graybee.response.admin.carousels;

public class CarouselPosition {

    private int id;

    private int position;

    public CarouselPosition() {
    }

    public CarouselPosition(int id, int position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
