package vn.graybee.account.service;

import vn.graybee.account.dto.request.AddressCreateRequest;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.response.users.AddressExistingDto;
import vn.graybee.response.users.DefaultAddressDto;
import vn.graybee.response.users.PersonalAddressDto;

import java.util.List;

public interface AddressService {

    BasicMessageResponse<List<PersonalAddressDto>> getAddressesByUserUid(String userUid);

    BasicMessageResponse<Integer> deleteAddressByIdAndUserUid(int id, String userUid);

    BasicMessageResponse<PersonalAddressDto> create(AddressCreateRequest request, String userUid);

    BasicMessageResponse<PersonalAddressDto> update(AddressCreateRequest request, int id, String userUid);

    BasicMessageResponse<DefaultAddressDto> updateDefaultAddress(int id, String userUid);

    void checkExistsById(Integer id);

    BasicMessageResponse<List<AddressExistingDto>> getAddressExistingByUserUidOrSessionId(String userUid, String sessionId);

}
