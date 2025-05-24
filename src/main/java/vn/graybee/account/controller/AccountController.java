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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.account.dto.request.AddressCreateRequest;
import vn.graybee.account.service.AddressService;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.order.dto.response.admin.CancelOrderResponse;
import vn.graybee.order.dto.response.user.OrderHistoryResponse;
import vn.graybee.order.service.OrderService;
import vn.graybee.product.service.ProductService;
import vn.graybee.response.favourites.ProductFavourite;
import vn.graybee.response.users.DefaultAddressDto;
import vn.graybee.response.users.PersonalAddressDto;

import java.util.List;

@RestController
@RequestMapping("${api.account}")
public class AccountController {

    private final AddressService addressService;

    private final ProductService productService;

    private final OrderService orderService;

    private final UserService userService;

    public AccountController(AddressService addressService, ProductService productService, OrderService orderService, UserService userService) {
        this.addressService = addressService;
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
    }


//    Favourites

    @GetMapping("/favourites")
    public ResponseEntity<BasicMessageResponse<List<ProductFavourite>>> getProductFavouritesByUserUid(@AuthenticationPrincipal CustomerPrincipal customerPrincipal) {
        String userUid = customerPrincipal.getCustomer().getUid();
        return ResponseEntity.ok(productService.getFavouritesByUserUid(userUid));
    }

    @PostMapping("/favourite/add")
    public ResponseEntity<BasicMessageResponse<?>> addToFavourite(
            @RequestParam("productId") long productId,
            @AuthenticationPrincipal CustomerPrincipal customerPrincipal
    ) {
        String userUid = customerPrincipal.getCustomer().getUid();
        return ResponseEntity.ok(productService.addToFavourite(userUid, productId));
    }

//    Address

    @PostMapping("/address/add")
    public ResponseEntity<BasicMessageResponse<PersonalAddressDto>> createAddress(
            @RequestBody @Valid AddressCreateRequest request,
            @AuthenticationPrincipal CustomerPrincipal customerPrincipal) {

        String userUid = customerPrincipal.getCustomer().getUid();
        return ResponseEntity.ok(addressService.create(request, userUid));
    }

    @GetMapping("/addresses")
    public ResponseEntity<BasicMessageResponse<List<PersonalAddressDto>>> getAddressesByUserUid(@AuthenticationPrincipal CustomerPrincipal customerPrincipal) {
        String userUid = customerPrincipal.getCustomer().getUid();
        return ResponseEntity.ok(addressService.getAddressesByUserUid(userUid));
    }

    @DeleteMapping("/address/delete")
    public ResponseEntity<BasicMessageResponse<Integer>> getAddressesByUserUid(
            @RequestParam("id") int id,
            @AuthenticationPrincipal CustomerPrincipal customerPrincipal) {
        String userUid = customerPrincipal.getCustomer().getUid();
        return ResponseEntity.ok(addressService.deleteAddressByIdAndUserUid(id, userUid));
    }

    @PutMapping("/address/update")
    public ResponseEntity<BasicMessageResponse<PersonalAddressDto>> updateAddress(
            @RequestBody @Valid AddressCreateRequest request,
            @RequestParam("id") int id,
            @AuthenticationPrincipal CustomerPrincipal customerPrincipal) {
        String userUid = customerPrincipal.getCustomer().getUid();
        return ResponseEntity.ok(addressService.update(request, id, userUid));
    }

    @PutMapping("/address/update-default")
    public ResponseEntity<BasicMessageResponse<DefaultAddressDto>> updateDefaultAddress(
            @RequestParam("id") int id,
            @AuthenticationPrincipal CustomerPrincipal customerPrincipal) {
        String userUid = customerPrincipal.getCustomer().getUid();
        return ResponseEntity.ok(addressService.updateDefaultAddress(id, userUid));
    }

//    Order

    @PutMapping("/order/cancel/{id}")
    public ResponseEntity<BasicMessageResponse<CancelOrderResponse>> cancelOrderById(
            @PathVariable("id") long id) {

        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    @GetMapping("/orders-history/{status}")
    public ResponseEntity<BasicMessageResponse<List<OrderHistoryResponse>>> getOrdersByUserUid(@PathVariable(value = "status", required = false) String status, @AuthenticationPrincipal CustomerPrincipal customerPrincipal) {
        String userUid = customerPrincipal.getCustomer().getUid();
        return ResponseEntity.ok(orderService.findOrderHistoriesByUserUid(userUid, status));
    }


}
