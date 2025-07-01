package vn.graybee.modules.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.common.utils.TextUtils;
import vn.graybee.modules.account.dto.request.AddressCreateRequest;
import vn.graybee.modules.account.dto.response.AddressResponse;
import vn.graybee.modules.account.dto.response.DefaultAddressDto;
import vn.graybee.modules.account.model.Address;
import vn.graybee.modules.account.repository.AddressRepository;
import vn.graybee.modules.account.service.AddressService;
import vn.graybee.modules.order.dto.request.CustomerInfoRequest;
import vn.graybee.modules.order.dto.request.ShippingInfoRequest;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final MessageSourceUtil messageSourceUtil;

    public AddressServiceImpl(AddressRepository addressRepository, MessageSourceUtil messageSourceUtil) {
        this.addressRepository = addressRepository;
        this.messageSourceUtil = messageSourceUtil;
    }

    private void checkExistsById(Integer id) {
        if (!addressRepository.checkExistsById(id)) {
            throw new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("account.customer.address.not-found"));
        }
    }

    @Override
    public BasicMessageResponse<List<AddressResponse>> getAllAddressesByCustomerId(Long customerId) {
        List<AddressResponse> response = addressRepository.findAllByCustomerId(customerId);

        if (response.isEmpty()) {
            return new BasicMessageResponse<>(200, messageSourceUtil.get("account.customer.address.empty-list"), response);
        }

        return new BasicMessageResponse<>(200, messageSourceUtil.get("account.customer.address.success-get-all"), response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> deleteAddressByIdAndCustomerId(Long customerId, Integer addressId) {

        checkExistsById(addressId);

        addressRepository.deleteByIdAndCustomerId(addressId, customerId);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("account.customer.address.success-delete"), addressId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<AddressResponse> createAddress(Long customerId, AddressCreateRequest request) {

        Address address = new Address();
        address.setCustomerId(customerId);
        address.setRecipientName(TextUtils.capitalizeEachWord(request.getRecipientName()));
        address.setPhone(request.getPhone());
        address.setCity(TextUtils.capitalizeEachWord(request.getCity()));
        address.setDistrict(TextUtils.capitalizeEachWord(request.getDistrict()));
        address.setCommune(TextUtils.capitalizeEachWord(request.getCommune()));
        address.setStreet(TextUtils.capitalize(request.getStreet()));

        if (request.isDefault()) {
            addressRepository.unsetDefaultAddressByUserUid(address.getCustomerId());
            address.setDefault(request.isDefault());
        }
        address = addressRepository.save(address);


        AddressResponse response = convertToAddressResponse(address);

        return new BasicMessageResponse<>(201, messageSourceUtil.get("account.customer.address.success-create"), response);
    }

    private AddressResponse convertToAddressResponse(Address address) {
        return new AddressResponse(
                address);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<AddressResponse> updateAddress(Long customerId, Integer addressId, AddressCreateRequest request) {

        Address updateAddress = addressRepository.findByIdAndCustomerId(customerId, addressId)
                .orElseThrow(() -> new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("account.customer.address.not-found")));

        updateAddress.setRecipientName(request.getRecipientName());
        updateAddress.setPhone(request.getPhone());
        updateAddress.setCity(request.getCity());
        updateAddress.setDistrict(request.getDistrict());
        updateAddress.setCommune(request.getCommune());
        updateAddress.setStreet(request.getStreet());

        if (request.isDefault()) {
            addressRepository.unsetDefaultAddressByUserUid(customerId);
            updateAddress.setDefault(request.isDefault());
        }

        updateAddress = addressRepository.save(updateAddress);

        AddressResponse response = convertToAddressResponse(updateAddress);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("account.customer.address.success-update"), response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<DefaultAddressDto> setDefaultAddress(Long customerId, Integer addressId) {
        checkExistsById(addressId);

        addressRepository.setOnlyOneDefaultAddress(customerId, addressId);

        return new BasicMessageResponse<>(200, messageSourceUtil.get("account.customer.address.success-update-default"),
                new DefaultAddressDto(addressId, true));
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Address getAddressByIdAndCustomerId(Long customerId, Long addressId, CustomerInfoRequest customerInfoRequest, ShippingInfoRequest shippingInfoRequest) {
        Address address = addressRepository.findAddressByIdAndCustomerId(customerId, addressId)
                .orElseGet(Address::new);

        boolean isNewAddress = address.getId() == null;
        if (isNewAddress) {
            address.setRecipientName(customerInfoRequest.getRecipientName());
            address.setPhone(customerInfoRequest.getRecipientPhone());
            address.setCity(shippingInfoRequest.getCity());
            address.setDistrict(shippingInfoRequest.getDistrict());
            address.setCommune(shippingInfoRequest.getCommune());
            address.setStreet(shippingInfoRequest.getStreetAddress());
            address.setDefault(true);

            addressRepository.save(address);
        }
        return address;
    }

    @Override
    public AddressResponse getAddressForUpdateById(long addressId) {
        return addressRepository.findAddressResponseById(addressId)
                .orElseThrow(() -> new BusinessCustomException(Constants.Common.global, messageSourceUtil.get("account.customer.address.not-found")));
    }

}
