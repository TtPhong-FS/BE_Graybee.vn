package vn.graybee.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.product.service.FavouriteService;
import vn.graybee.response.favourites.ProductFavourite;

import java.util.List;

@RestController
@RequestMapping()
public class FavouriteController {

    private final FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

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

}
