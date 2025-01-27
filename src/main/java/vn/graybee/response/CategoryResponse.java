package vn.graybee.response;

public class CategoryResponse {

    private long id;

    private String name;

    private String isDelete;


    public CategoryResponse(long id, String name, String isDelete) {
        this.id = id;
        this.name = name;
        this.isDelete = isDelete;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

}
