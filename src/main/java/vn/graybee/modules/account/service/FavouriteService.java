package vn.graybee.modules.account.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.account.dto.response.FavoriteProductResponse;

import java.util.List;

public interface FavouriteService {

    BasicMessageResponse<List<FavoriteProductResponse>> getFavoriteProductByUserUid(Long accountId);

    BasicMessageResponse<?> addFavoriteProduct(Long accountId, Long productId);

}
