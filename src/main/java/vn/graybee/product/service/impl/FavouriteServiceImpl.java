package vn.graybee.product.service.impl;

import org.springframework.stereotype.Service;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.product.dto.response.FavoriteProductResponse;
import vn.graybee.product.repository.FavouriteRepository;
import vn.graybee.product.service.FavouriteService;

import java.util.List;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    @Override
    public BasicMessageResponse<List<FavoriteProductResponse>> getFavoriteProductByUserUid(String userUid) {

        List<FavoriteProductResponse> favoriteProducts = favouriteRepository.findByUserUid(userUid);
        return null;
    }

}
