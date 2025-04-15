package vn.graybee.response.users;

public class PersonalAddressDto {

    private int id;

    private String phoneNumber;

    private String fullName;

    private String city;

    private String district;

    private String commune;

    private String streetAddress;

    private boolean isDefault;

    public PersonalAddressDto() {
    }

    public PersonalAddressDto(int id, String phoneNumber, String fullName, String city, String district, String commune, String streetAddress, boolean isDefault) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.city = city;
        this.district = district;
        this.commune = commune;
        this.streetAddress = streetAddress;
        this.isDefault = isDefault;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

}
