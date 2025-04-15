package vn.graybee.services.users;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.users.AddressCreateRequest;
import vn.graybee.response.users.AddressExistingDto;
import vn.graybee.response.users.DefaultAddressDto;
import vn.graybee.response.users.PersonalAddressDto;

import java.util.List;

public interface AddressService {

    BasicMessageResponse<List<PersonalAddressDto>> getAddressesByUserUid(int userUid);

    BasicMessageResponse<Integer> deleteAddressByIdAndUserUid(int id, int userUid);

    BasicMessageResponse<PersonalAddressDto> create(AddressCreateRequest request, int userUid);

    BasicMessageResponse<PersonalAddressDto> update(AddressCreateRequest request, int id, int userUid);

    BasicMessageResponse<DefaultAddressDto> updateDefaultAddress(int id, int userUid);

    void checkExistsById(Integer id);

    BasicMessageResponse<List<AddressExistingDto>> getAddressExistingByUserUidOrSessionId(Integer userUid, String sessionId);

}
