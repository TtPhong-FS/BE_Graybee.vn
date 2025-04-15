package vn.graybee.controllers.accounts;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.UserPrincipal;
import vn.graybee.requests.users.AddressCreateRequest;
import vn.graybee.requests.users.UpdateProfileRequest;
import vn.graybee.response.admin.orders.CancelOrderResponse;
import vn.graybee.response.favourites.ProductFavourite;
import vn.graybee.response.orders.OrderHistoryResponse;
import vn.graybee.response.users.AddressExistingDto;
import vn.graybee.response.users.DefaultAddressDto;
import vn.graybee.response.users.PersonalAddressDto;
import vn.graybee.response.users.UserProfileResponse;
import vn.graybee.services.orders.OrderService;
import vn.graybee.services.products.ProductService_public;
import vn.graybee.services.users.AddressService;
import vn.graybee.services.users.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AddressService addressService;

    private final ProductService_public productServicePublic;

    private final OrderService orderService;

    private final UserService userService;

    public AccountController(AddressService addressService, ProductService_public productServicePublic, OrderService orderService, UserService userService) {
        this.addressService = addressService;
        this.productServicePublic = productServicePublic;
        this.orderService = orderService;
        this.userService = userService;
    }

//    Profile

    @GetMapping("/profile")
    public ResponseEntity<BasicMessageResponse<UserProfileResponse>> getProfileByUserUid(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userUid = userPrincipal.getUser().getUserUid();
        return ResponseEntity.ok(userService.getProfileByUid(userUid));
    }

    @PutMapping("/profile/update")
    public ResponseEntity<BasicMessageResponse<UserProfileResponse>> updateProfile(
            @RequestBody @Valid UpdateProfileRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Integer userUid = userPrincipal.getUser().getUserUid();
        return ResponseEntity.ok(userService.update(request, userUid));
    }

//    Favourites

    @GetMapping("/favourites")
    public ResponseEntity<BasicMessageResponse<List<ProductFavourite>>> getProductFavouritesByUserUid(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userUid = userPrincipal.getUser().getUserUid();
        return ResponseEntity.ok(productServicePublic.getFavouritesByUserUid(userUid));
    }

    @PostMapping("/favourite/add")
    public ResponseEntity<BasicMessageResponse<?>> addToFavourite(
            @RequestParam("productId") long productId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Integer userUid = userPrincipal.getUser().getUserUid();
        return ResponseEntity.ok(productServicePublic.addToFavourite(userUid, productId));
    }

//    Address

    @GetMapping("/address-existing")
    public ResponseEntity<BasicMessageResponse<List<AddressExistingDto>>> getAddressExistingByUserUidOrSessionId(
            @CookieValue(value = "sessionId", required = false) String sessionId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        Integer userUid = userPrincipal.getUser().getUserUid();
        return ResponseEntity.ok(addressService.getAddressExistingByUserUidOrSessionId(userUid, sessionId));
    }

    @PostMapping("/address/add")
    public ResponseEntity<BasicMessageResponse<PersonalAddressDto>> createAddress(
            @RequestBody @Valid AddressCreateRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Integer userUid = userPrincipal.getUser().getUserUid();
        return ResponseEntity.ok(addressService.create(request, userUid));
    }

    @GetMapping("/addresses")
    public ResponseEntity<BasicMessageResponse<List<PersonalAddressDto>>> getAddressesByUserUid(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userUid = userPrincipal.getUser().getUserUid();
        return ResponseEntity.ok(addressService.getAddressesByUserUid(userUid));
    }

    @DeleteMapping("/address/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> getAddressesByUserUid(
            @RequestParam("id") int id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userUid = userPrincipal.getUser().getUserUid();
        return ResponseEntity.ok(addressService.deleteAddressByIdAndUserUid(id, userUid));
    }

    @PutMapping("/address/update")
    public ResponseEntity<BasicMessageResponse<PersonalAddressDto>> updateAddress(
            @RequestBody @Valid AddressCreateRequest request,
            @RequestParam("id") int id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userUid = userPrincipal.getUser().getUserUid();
        return ResponseEntity.ok(addressService.update(request, id, userUid));
    }

    @PutMapping("/address/update-default")
    public ResponseEntity<BasicMessageResponse<DefaultAddressDto>> updateDefaultAddress(
            @RequestParam("id") int id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userUid = userPrincipal.getUser().getUserUid();
        return ResponseEntity.ok(addressService.updateDefaultAddress(id, userUid));
    }

//    Order

    @PutMapping("/order/cancel/{id}")
    public ResponseEntity<BasicMessageResponse<CancelOrderResponse>> cancelOrderByIdAndUserUid(
            @PathVariable("id") long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userUid = userPrincipal.getUser().getUserUid();
        return ResponseEntity.ok(orderService.cancelOrder(id, userUid));
    }

    @GetMapping("/orders-history/{status}")
    public ResponseEntity<BasicMessageResponse<List<OrderHistoryResponse>>> getOrdersByUserUid(@PathVariable(value = "status", required = false) String status, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userUid = userPrincipal.getUser().getUserUid();
        return ResponseEntity.ok(orderService.findOrderHistoriesByUserUid(userUid, status));
    }


}
