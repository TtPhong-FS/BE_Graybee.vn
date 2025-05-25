package vn.graybee.account.dto.response;

import vn.graybee.account.model.Address;

public class AddressResponse {

    private Integer id;

    private String phone;

    private String recipientName;

    private String city;

    private String district;

    private String commune;

    private String street;

    private boolean isDefault;

    public AddressResponse() {
    }

    public AddressResponse(Address address) {
        this.id = address.getId();
        this.phone = address.getPhone();
        this.recipientName = address.getRecipientName();
        this.city = address.getCity();
        this.district = address.getDistrict();
        this.commune = address.getCommune();
        this.street = address.getStreet();
        this.isDefault = address.isDefault();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

}
