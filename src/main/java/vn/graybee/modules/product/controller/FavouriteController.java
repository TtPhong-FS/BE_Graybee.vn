package vn.graybee.modules.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.product.dto.response.FavoriteProductResponse;
import vn.graybee.modules.product.service.FavouriteService;

import java.util.List;

@RestController
@RequestMapping("${api.privateApi.favorites}")
public class FavouriteController {

    private final FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @GetMapping("/favourites")
    public ResponseEntity<BasicMessageResponse<List<FavoriteProductResponse>>> getFavoriteProductByUserUid(@AuthenticationPrincipal UserDetail userDetail) {
        Long accountId = userDetail.user().getId();
        return ResponseEntity.ok(favouriteService.getFavoriteProductByUserUid(accountId));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<BasicMessageResponse<?>> addFavoriteProduct(
            @PathVariable("productId") long productId,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        Long accountId = userDetail.user().getId();
        return ResponseEntity.ok(favouriteService.addFavoriteProduct(accountId, productId));
    }

}
