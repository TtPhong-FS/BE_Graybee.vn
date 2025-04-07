package vn.graybee.serviceImps.products;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantProduct;
import vn.graybee.constants.ConstantUser;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.Favourite;
import vn.graybee.repositories.products.ProductDescriptionRepository;
import vn.graybee.repositories.products.ProductImageRepository;
import vn.graybee.repositories.products.ProductRepository;
import vn.graybee.repositories.products.ReviewCommentRepository;
import vn.graybee.repositories.users.FavouriteRepository;
import vn.graybee.response.favourites.ProductFavourite;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductDetailResponse;
import vn.graybee.response.publics.products.ProductPriceResponse;
import vn.graybee.response.publics.products.ReviewCommentDto;
import vn.graybee.services.products.ProductService_public;
import vn.graybee.services.products.ProductStatisticService;

import java.util.List;

@Service
public class ProductServiceImpl_public implements ProductService_public {

    private final ProductRepository productRepository;

    private final FavouriteRepository favouriteRepository;

    private final ProductStatisticService productStatisticService;

    private final ProductImageRepository productImageRepository;

    private final ProductDescriptionRepository productDescriptionRepository;

    private final ReviewCommentRepository reviewCommentRepository;

    public ProductServiceImpl_public(ProductRepository productRepository, FavouriteRepository favouriteRepository, ProductStatisticService productStatisticService, ProductImageRepository productImageRepository, ProductDescriptionRepository productDescriptionRepository, ReviewCommentRepository reviewCommentRepository) {
        this.productRepository = productRepository;
        this.favouriteRepository = favouriteRepository;
        this.productStatisticService = productStatisticService;
        this.productImageRepository = productImageRepository;
        this.productDescriptionRepository = productDescriptionRepository;
        this.reviewCommentRepository = reviewCommentRepository;
    }

    @Override
    public BasicMessageResponse<List<ProductBasicResponse>> fetchProductsFromClient() {

        return null;
    }

    @Override
    public BasicMessageResponse<List<ProductBasicResponse>> findByCategoryAndManufacturer(String categoryName, String manufacturerName) {
        List<ProductBasicResponse> products = productRepository.findByCategoryAndManufacturer(categoryName, manufacturerName);
        if (products.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantGeneral.empty_list, products);
        }

        return new BasicMessageResponse<>(200, ConstantProduct.success_fetch_products, products);
    }

    @Override
    public BasicMessageResponse<List<ProductBasicResponse>> findByCategoryAndSubcategoryAndTag(String categoryName, String subcategoryName, String tagName) {
        List<ProductBasicResponse> products = productRepository.findByCategoryAndSubcategoryAndTag(categoryName, subcategoryName, tagName);
        if (products.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantGeneral.empty_list, products);
        }

        return new BasicMessageResponse<>(200, ConstantProduct.success_fetch_products, products);
    }

    @Override
    public ProductPriceResponse getPriceById(long id) {
        return productRepository.getPriceById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));
    }

    @Override
    public ProductBasicResponse findBasicToAddToCartById(long id) {
        return productRepository.findBasicToAddToCart(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

    }

    @Override
    public BasicMessageResponse<ProductDetailResponse> getDetailById(long id) {
        ProductDetailResponse response = productRepository.getDetailByProductId(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        List<String> images = productImageRepository.findImageUrlsByProductId(response.getId());
        List<ReviewCommentDto> reviews = reviewCommentRepository.getReviewsByProductId(response.getId());
        String description = productDescriptionRepository.getDescriptionByProductId(response.getId());


        response.setImages(images);
        response.setDescription(description);
        response.setReviews(reviews);

        productStatisticService.updateViewCount(response.getId());

        return new BasicMessageResponse<>(200, null, response);
    }

    @Override
    public BasicMessageResponse<List<ProductFavourite>> getFavouritesByUserUid(Integer userUid) {

        List<ProductFavourite> productFavourites = favouriteRepository.getProductFavouritesByUserUId(userUid);

        if (productFavourites.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantUser.empty_favourites, productFavourites);
        }

        return new BasicMessageResponse<>(200, ConstantUser.success_get_favourites, productFavourites);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<?> addToFavourite(int userUid, long productId) {

        ProductFavourite product = productRepository.findToAddToFavourite(productId)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        boolean isFavourite = false;

        List<ProductFavourite> favourites = favouriteRepository.getProductFavouritesByUserUId(userUid);

        for (ProductFavourite favourite : favourites) {
            if (favourite.getId().equals(productId)) {
                isFavourite = true;
                break;
            }
        }

        if (isFavourite) {
            favouriteRepository.deleteByProductIdExistsAndUserUid(productId, userUid);
            return new BasicMessageResponse<>(200, ConstantUser.success_remove_favourite, product.getId());
        } else {
            Favourite favourite = new Favourite(userUid, productId);
            favouriteRepository.save(favourite);
        }

        return new BasicMessageResponse<>(200, ConstantUser.success_add_favourite, product);
    }

    @Override
    public BasicMessageResponse<List<ProductBasicResponse>> findByCategoryName(String categoryName) {
        List<ProductBasicResponse> products = productRepository.findProductsByCategoryName(categoryName);

        if (products.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantGeneral.empty_list, products);
        }

        return new BasicMessageResponse<>(200, ConstantProduct.success_fetch_products, products);
    }

}
