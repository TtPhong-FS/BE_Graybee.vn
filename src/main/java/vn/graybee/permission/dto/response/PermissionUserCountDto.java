package vn.graybee.permission.dto.response;

public class PermissionUserCountDto {

    private int id;

    private int userCount;

    public PermissionUserCountDto() {
    }

    public PermissionUserCountDto(int id, int userCount) {
        this.id = id;
        this.userCount = userCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

}
