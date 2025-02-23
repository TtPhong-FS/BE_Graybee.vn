package vn.graybee.response.categories;

public class CategoryStatusResponse {

    private int id;

    private boolean isDeleted;

    public CategoryStatusResponse(int id, boolean isDeleted) {
        this.id = id;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

}
