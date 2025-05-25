package vn.graybee.account.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.account.dto.request.AddressCreateRequest;
import vn.graybee.account.dto.response.AddressResponse;
import vn.graybee.account.security.UserDetail;
import vn.graybee.account.service.AddressService;
import vn.graybee.account.service.CustomerService;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.response.users.DefaultAddressDto;

import java.util.List;

@RestController
@RequestMapping("${api.address}")
public class AddressController {

    private final AddressService addressService;

    private final CustomerService customerService;

    public AddressController(AddressService addressService, CustomerService customerService) {
        this.addressService = addressService;
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<BasicMessageResponse<AddressResponse>> createAddress(
            @RequestBody @Valid AddressCreateRequest request,
            @AuthenticationPrincipal UserDetail userDetail) {

        Long accountId = userDetail.getUser().getId();
        Long customerId = customerService.getIdByAccountId(accountId);
        return ResponseEntity.ok(addressService.createAddress(customerId, request));
    }

    @GetMapping
    public ResponseEntity<BasicMessageResponse<List<AddressResponse>>> getAllAddressesByCustomerId(
            @AuthenticationPrincipal UserDetail userDetail
    ) {

        Long accountId = userDetail.getUser().getId();
        Long customerId = customerService.getIdByAccountId(accountId);
        return ResponseEntity.ok(addressService.getAllAddressesByCustomerId(customerId));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<BasicMessageResponse<Integer>> deleteAddressByIdAndCustomerId(
            @PathVariable("addressId") Integer addressId,
            @AuthenticationPrincipal UserDetail userDetail) {

        Long accountId = userDetail.getUser().getId();
        Long customerId = customerService.getIdByAccountId(accountId);
        return ResponseEntity.ok(addressService.deleteAddressByIdAndCustomerId(customerId, addressId));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<BasicMessageResponse<AddressResponse>> updateAddress(
            @RequestBody @Valid AddressCreateRequest request,
            @PathVariable("addressId") Integer addressId,
            @AuthenticationPrincipal UserDetail userDetail) {

        Long accountId = userDetail.getUser().getId();
        Long customerId = customerService.getIdByAccountId(accountId);
        return ResponseEntity.ok(addressService.updateAddress(customerId, addressId, request));
    }

    @PutMapping("/default-address/{addressId}")
    public ResponseEntity<BasicMessageResponse<DefaultAddressDto>> setDefaultAddress(
            @PathVariable("addressId") Integer addressId,
            @AuthenticationPrincipal UserDetail userDetail) {

        Long accountId = userDetail.getUser().getId();
        Long customerId = customerService.getIdByAccountId(accountId);

        return ResponseEntity.ok(addressService.setDefaultAddress(customerId, addressId));
    }

}
