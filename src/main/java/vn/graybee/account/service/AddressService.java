package vn.graybee.account.service;

import vn.graybee.account.dto.request.AddressCreateRequest;
import vn.graybee.account.dto.response.AddressResponse;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.response.users.DefaultAddressDto;

import java.util.List;

public interface AddressService {

    BasicMessageResponse<List<AddressResponse>> getAllAddressesByCustomerId(Long customerId);

    BasicMessageResponse<Integer> deleteAddressByIdAndCustomerId(Long customerId, Integer addressId);

    BasicMessageResponse<AddressResponse> createAddress(Long customerId, AddressCreateRequest request);

    BasicMessageResponse<AddressResponse> updateAddress(Long customerId, Integer addressId, AddressCreateRequest request);

    BasicMessageResponse<DefaultAddressDto> setDefaultAddress(Long customerId, Integer addressId);

    AddressResponse getAddressDefaultByIdAndCustomerId(Long customerId, Integer addressId);

}
