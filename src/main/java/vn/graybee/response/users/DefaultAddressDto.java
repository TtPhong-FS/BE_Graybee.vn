package vn.graybee.response.users;

public class DefaultAddressDto {

    private int id;

    private boolean isDefault;

    public DefaultAddressDto() {
    }

    public DefaultAddressDto(int id, boolean isDefault) {
        this.id = id;
        this.isDefault = isDefault;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

}
