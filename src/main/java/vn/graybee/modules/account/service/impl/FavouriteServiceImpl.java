package vn.graybee.modules.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.dto.response.FavoriteProductResponse;
import vn.graybee.modules.account.model.Favorite;
import vn.graybee.modules.account.repository.FavouriteRepository;
import vn.graybee.modules.account.service.FavouriteService;
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
                ? messageSourceUtil.get("account.favorite.list.empty")
                : messageSourceUtil.get("account.favorite.success_find_all");

        return new BasicMessageResponse<>(200, message, favoriteProducts);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<?> addFavoriteProduct(Long accountId, Long productId) {

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
            return new BasicMessageResponse<>(200, messageSourceUtil.get("account.favorite.remove.success"), productId);
        }

        Favorite favourite = new Favorite();
        favourite.setAccountId(accountId);
        favourite.setProductId(productId);

        favouriteRepository.save(favourite);

        return new BasicMessageResponse<>(201, messageSourceUtil.get("account.favorite.success.add"), productService.getProductFavouriteById(productId));
    }

}
