package vn.graybee.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.account.dto.request.AddressCreateRequest;
import vn.graybee.account.model.Address;
import vn.graybee.account.repository.AddressRepository;
import vn.graybee.account.service.AddressService;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantUser;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.TextUtils;
import vn.graybee.response.users.AddressExistingDto;
import vn.graybee.response.users.DefaultAddressDto;
import vn.graybee.response.users.PersonalAddressDto;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public BasicMessageResponse<List<PersonalAddressDto>> getAddressesByUserUid(String userUid) {
        List<PersonalAddressDto> response = addressRepository.getAddressesByUserUid(userUid);
        return new BasicMessageResponse<>(200, null, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> deleteAddressByIdAndUserUid(int id, String userUid) {

        checkExistsById(id);

        addressRepository.deleteByIdAndUserUid(id, userUid);

        return new BasicMessageResponse<>(200, ConstantUser.success_delete_address, id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<PersonalAddressDto> create(AddressCreateRequest request, String userUid) {

        Address address = new Address();
        address.setUserUid(userUid);
        address.setFullName(TextUtils.capitalizeEachWord(request.getFullName()));
        address.setPhoneNumber(request.getPhoneNumber());
        address.setCity(TextUtils.capitalizeEachWord(request.getCity()));
        address.setDistrict(TextUtils.capitalizeEachWord(request.getDistrict()));
        address.setCommune(TextUtils.capitalizeEachWord(request.getCommune()));
        address.setStreetAddress(TextUtils.capitalize(request.getStreetAddress()));

        if (request.isDefault()) {
            addressRepository.updateAllDefaultToFalseByUserUid(address.getUserUid());
        }
        address.setDefault(request.isDefault());
        address = addressRepository.save(address);


        PersonalAddressDto response = new PersonalAddressDto(
                address.getId(),
                address.getPhoneNumber(),
                address.getFullName(),
                address.getCity(),
                address.getDistrict(),
                address.getCommune(),
                address.getStreetAddress(),
                address.isDefault());

        return new BasicMessageResponse<>(201, ConstantUser.success_add_address, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<PersonalAddressDto> update(AddressCreateRequest request, int id, String userUid) {

        Address updateAddress = addressRepository.findByIdAndUserUid(id, userUid)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantUser.address_not_exists));

        updateAddress.setFullName(request.getFullName());
        updateAddress.setPhoneNumber(request.getPhoneNumber());
        updateAddress.setCity(request.getCity());
        updateAddress.setDistrict(request.getDistrict());
        updateAddress.setCommune(request.getCommune());
        updateAddress.setStreetAddress(request.getStreetAddress());

        if (request.isDefault()) {
            addressRepository.updateAllDefaultToFalseByUserUid(userUid);
        }

        updateAddress.setDefault(request.isDefault());
        updateAddress = addressRepository.save(updateAddress);

        PersonalAddressDto response = new PersonalAddressDto(
                updateAddress.getId(),
                updateAddress.getPhoneNumber(),
                updateAddress.getFullName(),
                updateAddress.getCity(),
                updateAddress.getDistrict(),
                updateAddress.getCommune(),
                updateAddress.getStreetAddress(),
                updateAddress.isDefault());

        return new BasicMessageResponse<>(200, ConstantUser.success_update_address, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<DefaultAddressDto> updateDefaultAddress(int id, String userUid) {

        checkExistsById(id);

        addressRepository.setOnlyOneDefaultAddress(id, userUid);

        DefaultAddressDto response = new DefaultAddressDto(id, true);

        return new BasicMessageResponse<>(200, ConstantUser.success_update_default_address, response);
    }

    @Override
    public void checkExistsById(Integer id) {
        if (!addressRepository.checkExistsById(id)) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantUser.address_not_exists);
        }
    }

    @Override
    public BasicMessageResponse<List<AddressExistingDto>> getAddressExistingByUserUidOrSessionId(String userUid, String sessionId) {
        if (userUid != null && sessionId != null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.invalid_request);
        }
        List<AddressExistingDto> response = addressRepository.getAddressExistingByUserUidOrSessionId(userUid, sessionId);
        return new BasicMessageResponse<>(200, null, response);
    }

}
