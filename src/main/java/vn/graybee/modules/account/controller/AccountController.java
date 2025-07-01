package vn.graybee.modules.account.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.account.dto.request.AddressCreateRequest;
import vn.graybee.modules.account.dto.response.AddressResponse;
import vn.graybee.modules.account.dto.response.DefaultAddressDto;
import vn.graybee.modules.account.dto.response.FavoriteProductResponse;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.account.service.AddressService;
import vn.graybee.modules.account.service.CustomerService;
import vn.graybee.modules.account.service.FavouriteService;
import vn.graybee.modules.order.dto.response.OrderDetailDto;
import vn.graybee.modules.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.modules.order.dto.response.user.OrderHistoryResponse;
import vn.graybee.modules.order.service.OrderService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.privateApi.account}")
public class AccountController {

    private final FavouriteService favouriteService;

    private final OrderService orderService;

    private final AddressService addressService;

    private final CustomerService customerService;


    @GetMapping("/favourites")
    public ResponseEntity<BasicMessageResponse<List<FavoriteProductResponse>>> getFavoriteProductByUserUid(@AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = userDetail.user().getId();
        return ResponseEntity.ok(favouriteService.getFavoriteProductByUserUid(accountId));
    }

    @PostMapping("/favourites/{productId}")
    public ResponseEntity<BasicMessageResponse<?>> addFavoriteProduct(
            @PathVariable("productId") long productId,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        Long accountId = userDetail.user().getId();
        return ResponseEntity.ok(favouriteService.addFavoriteProduct(accountId, productId));
    }

    @GetMapping("/orders/history")
    public ResponseEntity<BasicMessageResponse<List<OrderHistoryResponse>>> getOrderHistoryByAccountId(@AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = userDetail.user().getId();

        List<OrderHistoryResponse> orders = orderService.getOrderHistoryByAccountId(accountId);

        final String msg = orders.isEmpty() ? "Bạn chưa có lịch sử đơn hàng nào" : "Lấy lịch sử đơn hàng thành công";
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        orders,
                        msg
                )
        );
    }

    @GetMapping("/orders/detail/{code}")
    public ResponseEntity<BasicMessageResponse<OrderDetailDto>> getOrderDetailByCode(
            @AuthenticationPrincipal UserDetail userDetail,
            @PathVariable("code") String code
    ) {
        Long accountId = userDetail.user().getId();

        OrderDetailDto orderDetailDto = orderService.getOrderDetailByCodeAndAccountId(code, accountId);

        return ResponseEntity.ok(
                MessageBuilder.ok(
                        orderDetailDto,
                        null
                )
        );
    }

    @PutMapping("/orders/cancel/{code}")
    public ResponseEntity<BasicMessageResponse<CancelOrderResponse>> cancelOrderByCode(
            @PathVariable("code") String code) {
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        orderService.cancelOrderByCode(code),
                        "Huỷ đơn hàng thành công"
                )
        );
    }


    @PostMapping("/addresses")
    public ResponseEntity<BasicMessageResponse<AddressResponse>> createAddress(
            @RequestBody @Valid AddressCreateRequest request,
            @AuthenticationPrincipal UserDetail userDetail) {

        Long accountId = userDetail.user().getId();
        Long customerId = customerService.getIdByAccountId(accountId);
        return ResponseEntity.ok(addressService.createAddress(customerId, request));
    }

    @GetMapping("/addresses")
    public ResponseEntity<BasicMessageResponse<List<AddressResponse>>> getAllAddressesByCustomerId(
            @AuthenticationPrincipal UserDetail userDetail
    ) {

        Long accountId = userDetail.user().getId();
        Long customerId = customerService.getIdByAccountId(accountId);
        return ResponseEntity.ok(addressService.getAllAddressesByCustomerId(customerId));
    }

    @GetMapping("/addresses/for-update/{addressId}")
    public ResponseEntity<BasicMessageResponse<AddressResponse>> getAddressForUpdate(
            @PathVariable("addressId") long addressId
    ) {

        return ResponseEntity.ok(
                MessageBuilder.ok(
                        addressService.getAddressForUpdateById(addressId),
                        null
                )
        );
    }


    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<BasicMessageResponse<Integer>> deleteAddressByIdAndCustomerId(
            @PathVariable("addressId") Integer addressId,
            @AuthenticationPrincipal UserDetail userDetail) {

        Long accountId = userDetail.user().getId();
        Long customerId = customerService.getIdByAccountId(accountId);
        return ResponseEntity.ok(addressService.deleteAddressByIdAndCustomerId(customerId, addressId));
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<BasicMessageResponse<AddressResponse>> updateAddress(
            @RequestBody @Valid AddressCreateRequest request,
            @PathVariable("addressId") Integer addressId,
            @AuthenticationPrincipal UserDetail userDetail) {

        Long accountId = userDetail.user().getId();
        Long customerId = customerService.getIdByAccountId(accountId);
        return ResponseEntity.ok(addressService.updateAddress(customerId, addressId, request));
    }

    @PutMapping("/addresses/default/{addressId}")
    public ResponseEntity<BasicMessageResponse<DefaultAddressDto>> setDefaultAddress(
            @PathVariable("addressId") Integer addressId,
            @AuthenticationPrincipal UserDetail userDetail) {

        Long accountId = userDetail.user().getId();
        Long customerId = customerService.getIdByAccountId(accountId);

        return ResponseEntity.ok(addressService.setDefaultAddress(customerId, addressId));
    }

}
