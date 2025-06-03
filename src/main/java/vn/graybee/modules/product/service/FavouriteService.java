package vn.graybee.modules.product.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.product.dto.response.FavoriteProductResponse;

import java.util.List;

public interface FavouriteService {

    BasicMessageResponse<List<FavoriteProductResponse>> getFavoriteProductByUserUid(Long accountId);

    BasicMessageResponse<FavoriteProductResponse> addFavoriteProduct(Long accountId, Long productId);

}
