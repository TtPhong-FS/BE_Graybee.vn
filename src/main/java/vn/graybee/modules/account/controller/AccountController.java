package vn.graybee.modules.account.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.account.dto.response.FavoriteProductResponse;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.account.service.FavouriteService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.privateApi.account}")
public class AccountController {

    private final FavouriteService favouriteService;

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
