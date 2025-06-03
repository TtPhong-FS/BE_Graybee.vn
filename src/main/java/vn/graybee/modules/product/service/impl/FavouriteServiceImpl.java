package vn.graybee.modules.product.service.impl;

import org.springframework.stereotype.Service;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.product.dto.response.FavoriteProductResponse;
import vn.graybee.modules.product.model.Favourite;
import vn.graybee.modules.product.repository.FavouriteRepository;
import vn.graybee.modules.product.service.FavouriteService;
import vn.graybee.modules.product.service.ProductService;

import java.util.List;

@Service
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;

    private final ProductService productService;

    private final MessageSourceUtil messageSourceUtil;

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository, ProductService productService, MessageSourceUtil messageSourceUtil) {
        this.favouriteRepository = favouriteRepository;
        this.productService = productService;
        this.messageSourceUtil = messageSourceUtil;
    }

    @Override
    public BasicMessageResponse<List<FavoriteProductResponse>> getFavoriteProductByUserUid(Long accountId) {

        List<FavoriteProductResponse> favoriteProducts = favouriteRepository.findAllFavoriteProductByAccountId(accountId);

        final String message = favoriteProducts.isEmpty()
                ? messageSourceUtil.get("product.favourite.list.empty")
                : messageSourceUtil.get("product.favourite.success_find_all");

        return new BasicMessageResponse<>(200, message, favoriteProducts);
    }

    @Override
    public BasicMessageResponse<FavoriteProductResponse> addFavoriteProduct(Long accountId, Long productId) {

        boolean isFavorite = false;

        List<Long> productIdsExistsInFavourite = favouriteRepository.findProductIdsByAccountId(accountId);

        for (Long productIdExists : productIdsExistsInFavourite) {
            if (productIdExists.equals(productId)) {
                isFavorite = true;
                break;
            }
        }

        if (isFavorite) {
            favouriteRepository.deleteByAccountIdAndProductId(accountId, productId);
            return new BasicMessageResponse<>(200, messageSourceUtil.get("product.favourite.remove.success"), null);
        }

        Favourite favourite = new Favourite();
        favourite.setAccountId(accountId);
        favourite.setProductId(productId);

        favouriteRepository.save(favourite);

        return new BasicMessageResponse<>(201, messageSourceUtil.get("product.favourite.add.success"), productService.getProductFavouriteById(productId));
    }

}
