package vn.graybee.response.users;

public class UserAuthenDto {

    private String password;

    private String role;

    private Integer uid;

    private boolean isActive;

    public UserAuthenDto() {
    }

    public UserAuthenDto(String password, String role, Integer uid, boolean isActive) {
        this.password = password;
        this.role = role;
        this.uid = uid;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

}
