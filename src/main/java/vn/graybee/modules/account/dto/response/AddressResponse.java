package vn.graybee.modules.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.account.model.Address;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

    private long id;

    private String phone;

    private String recipientName;

    private String city;

    private String district;

    private String commune;

    private String street;

    private boolean isDefault;

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


}
