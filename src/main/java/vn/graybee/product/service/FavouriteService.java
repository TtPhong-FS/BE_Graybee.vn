package vn.graybee.product.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.product.dto.response.FavoriteProductResponse;

import java.util.List;

public interface FavouriteService {

    BasicMessageResponse<List<FavoriteProductResponse>> getFavoriteProductByUserUid(String userUid);

}
