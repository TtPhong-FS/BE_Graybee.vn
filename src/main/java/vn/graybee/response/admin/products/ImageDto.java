package vn.graybee.response.admin.products;

public class ImageDto {

    private int id;

    private String imageUrl;

    public ImageDto() {
    }

    public ImageDto(int id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
