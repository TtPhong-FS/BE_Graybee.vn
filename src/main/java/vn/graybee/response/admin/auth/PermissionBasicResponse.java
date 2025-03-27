package vn.graybee.response.admin.auth;

public class PermissionBasicResponse {

    private int id;

    private String name;

    public PermissionBasicResponse() {
    }

    public PermissionBasicResponse(int id, String name) {
        this.id = id;
        this.name = name;
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

}
