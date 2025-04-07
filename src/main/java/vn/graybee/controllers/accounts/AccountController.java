package vn.graybee.controllers.accounts;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.constants.ConstantAuth;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.users.AddressCreateRequest;
import vn.graybee.requests.users.UpdateProfileRequest;
import vn.graybee.response.favourites.ProductFavourite;
import vn.graybee.response.users.DefaultAddressDto;
import vn.graybee.response.users.PersonalAddressDto;
import vn.graybee.response.users.UserProfileResponse;
import vn.graybee.services.products.ProductService_public;
import vn.graybee.services.users.AddressService;
import vn.graybee.services.users.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AddressService addressService;

    private final ProductService_public productServicePublic;

    private final UserService userService;

    public AccountController(AddressService addressService, ProductService_public productServicePublic, UserService userService) {
        this.addressService = addressService;
        this.productServicePublic = productServicePublic;
        this.userService = userService;
    }

//    Profile

    @GetMapping("/profile")
    public ResponseEntity<BasicMessageResponse<UserProfileResponse>> getProfileByUserUid(@RequestHeader("Authorization") String token) {
        if (token == null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantAuth.missing_authorization);
        }
        String subToken = token.substring(7);
        Integer userUid = userService.getUidByToken(subToken);
        return ResponseEntity.ok(userService.getProfileByUid(userUid));
    }

    @PutMapping("/profile/update")
    public ResponseEntity<BasicMessageResponse<UserProfileResponse>> updateProfile(@RequestBody @Valid UpdateProfileRequest request, @RequestHeader("Authorization") String token) {
        if (token == null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantAuth.missing_authorization);
        }
        String subToken = token.substring(7);
        Integer userUid = userService.getUidByToken(subToken);
        return ResponseEntity.ok(userService.update(request, userUid));
    }

//    Favourites

    @GetMapping("/favourites")
    public ResponseEntity<BasicMessageResponse<List<ProductFavourite>>> getProductFavouritesByUserUid(@RequestHeader("Authorization") String token) {
        if (token == null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantAuth.missing_authorization);
        }
        String subToken = token.substring(7);
        Integer userUid = userService.getUidByToken(subToken);
        return ResponseEntity.ok(productServicePublic.getFavouritesByUserUid(userUid));
    }

    @PostMapping("/favourite/add")
    public ResponseEntity<BasicMessageResponse<?>> addToFavourite(@RequestParam("productId") long productId, @RequestHeader("Authorization") String token) {
        if (token == null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantAuth.missing_authorization);
        }
        String subToken = token.substring(7);
        Integer userUid = userService.getUidByToken(subToken);
        return ResponseEntity.ok(productServicePublic.addToFavourite(userUid, productId));
    }

//    Address

    @PostMapping("/address/add")
    public ResponseEntity<BasicMessageResponse<PersonalAddressDto>> createAddress(@RequestBody @Valid AddressCreateRequest request, @RequestHeader("Authorization") String token) {
        if (token == null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantAuth.missing_authorization);
        }
        String subToken = token.substring(7);
        Integer userUid = userService.getUidByToken(subToken);
        return ResponseEntity.ok(addressService.create(request, userUid));
    }

    @GetMapping("/addresses")
    public ResponseEntity<BasicMessageResponse<List<PersonalAddressDto>>> getAddressesByUserUid(@RequestHeader("Authorization") String token) {
        if (token == null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantAuth.missing_authorization);
        }
        String subToken = token.substring(7);
        Integer userUid = userService.getUidByToken(subToken);
        return ResponseEntity.ok(addressService.getAddressesByUserUid(userUid));
    }

    @DeleteMapping("/address/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> getAddressesByUserUid(@RequestHeader("Authorization") String token, @RequestParam("id") int id) {
        if (token == null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantAuth.missing_authorization);
        }
        String subToken = token.substring(7);
        Integer userUid = userService.getUidByToken(subToken);
        return ResponseEntity.ok(addressService.deleteAddressByIdAndUserUid(id, userUid));
    }

    @PutMapping("/address/update")
    public ResponseEntity<BasicMessageResponse<PersonalAddressDto>> updateAddress(@RequestBody @Valid AddressCreateRequest request, @RequestHeader("Authorization") String token, @RequestParam("id") int id) {
        if (token == null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantAuth.missing_authorization);
        }
        String subToken = token.substring(7);
        Integer userUid = userService.getUidByToken(subToken);
        return ResponseEntity.ok(addressService.update(request, id, userUid));
    }

    @PutMapping("/address/update-default")
    public ResponseEntity<BasicMessageResponse<DefaultAddressDto>> updateDefaultAddress(@RequestHeader("Authorization") String token, @RequestParam("id") int id) {
        if (token == null) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantAuth.missing_authorization);
        }
        String subToken = token.substring(7);
        Integer userUid = userService.getUidByToken(subToken);
        return ResponseEntity.ok(addressService.updateDefaultAddress(id, userUid));
    }

}
