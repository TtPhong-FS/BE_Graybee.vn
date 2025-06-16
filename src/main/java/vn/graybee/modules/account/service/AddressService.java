package vn.graybee.modules.account.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.account.dto.request.AddressCreateRequest;
import vn.graybee.modules.account.dto.response.AddressResponse;
import vn.graybee.modules.account.dto.response.DefaultAddressDto;
import vn.graybee.modules.account.model.Address;
import vn.graybee.modules.order.dto.request.CustomerInfoRequest;
import vn.graybee.modules.order.dto.request.ShippingInfoRequest;

import java.util.List;

public interface AddressService {

    BasicMessageResponse<List<AddressResponse>> getAllAddressesByCustomerId(Long customerId);

    BasicMessageResponse<Integer> deleteAddressByIdAndCustomerId(Long customerId, Integer addressId);

    BasicMessageResponse<AddressResponse> createAddress(Long customerId, AddressCreateRequest request);

    BasicMessageResponse<AddressResponse> updateAddress(Long customerId, Integer addressId, AddressCreateRequest request);

    BasicMessageResponse<DefaultAddressDto> setDefaultAddress(Long customerId, Integer addressId);

    Address getAddressByIdAndCustomerId(Long customerId, Long addressId, CustomerInfoRequest customerInfoRequest, ShippingInfoRequest shippingInfoRequest);

}
