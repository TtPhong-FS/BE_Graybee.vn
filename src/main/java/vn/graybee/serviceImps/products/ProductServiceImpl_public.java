package vn.graybee.serviceImps.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantProduct;
import vn.graybee.constants.ConstantUser;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.messages.other.PaginationInfo;
import vn.graybee.messages.other.SortInfo;
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
import vn.graybee.services.products.RedisProductService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl_public implements ProductService_public {

    private static final String PRODUCT_DETAIL_KEY = "product:detail:";

    private static final String PRODUCT_LIST_KEY = "product:list:";

    private final ProductRepository productRepository;

    private final FavouriteRepository favouriteRepository;

    private final ProductStatisticService productStatisticService;

    private final ProductImageRepository productImageRepository;

    private final ProductDescriptionRepository productDescriptionRepository;

    private final ReviewCommentRepository reviewCommentRepository;

    private final RedisProductService redisProductService;

    public ProductServiceImpl_public(ProductRepository productRepository, FavouriteRepository favouriteRepository, ProductStatisticService productStatisticService, ProductImageRepository productImageRepository, ProductDescriptionRepository productDescriptionRepository, ReviewCommentRepository reviewCommentRepository, RedisProductService redisProductService) {
        this.productRepository = productRepository;
        this.favouriteRepository = favouriteRepository;
        this.productStatisticService = productStatisticService;
        this.productImageRepository = productImageRepository;
        this.productDescriptionRepository = productDescriptionRepository;
        this.reviewCommentRepository = reviewCommentRepository;
        this.redisProductService = redisProductService;
    }

    public PaginationInfo getPagination(Page<ProductBasicResponse> page) {
        return new PaginationInfo(
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize()
        );
    }

    @Override
    public MessageResponse<List<ProductBasicResponse>> findByCategoryName(String categoryName, int page, int size, String sortBy, String order) {

        List<ProductBasicResponse> cachedProducts = redisProductService.getCachedListProductBasicBySortedSet(categoryName, sortBy, page, size, true);

        if (cachedProducts != null && !cachedProducts.isEmpty()) {

            long totalElements = cachedProducts.size();

            int totalPages = (int) Math.ceil((double) totalElements / size);

            PaginationInfo paginationInfo = new PaginationInfo(
                    page, totalPages, totalElements, size
            );

            SortInfo sortInfo = new SortInfo(
                    sortBy, order
            );

            return new MessageResponse<>(200, ConstantProduct.success_fetch_products, cachedProducts, paginationInfo, sortInfo);
        }

        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductBasicResponse> productPage = productRepository.findProductsByCategoryName(categoryName, pageable);

        PaginationInfo paginationInfo = new PaginationInfo(
                productPage.getNumber(), productPage.getTotalPages(), productPage.getTotalElements(), productPage.getSize()
        );

        SortInfo sortInfo = new SortInfo(
                sortBy, order
        );

        String message = productPage.isEmpty()
                ? ConstantGeneral.empty_list
                : ConstantProduct.success_fetch_products;

        redisProductService.cacheListProductBasicBySortedSet(productPage.getContent(), categoryName, sortBy, 12, TimeUnit.HOURS);

        return new MessageResponse<>(200, message, productPage.getContent(), paginationInfo, sortInfo);
    }


    @Override
    public MessageResponse<List<ProductBasicResponse>> findByCategoryAndManufacturer(String categoryName, String manufacturerName, int page, int size, String sortBy, String order) {

        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductBasicResponse> productPage = productRepository.findByCategoryAndManufacturer(categoryName, manufacturerName, pageable);

        PaginationInfo paginationInfo = getPagination(productPage);

        SortInfo sortInfo = new SortInfo(sortBy, order);


        String message = productPage.isEmpty()
                ? ConstantGeneral.empty_list
                : ConstantProduct.success_fetch_products;

        return new MessageResponse<>(200, message, productPage.getContent(), paginationInfo, sortInfo);
    }

    @Override
    public MessageResponse<List<ProductBasicResponse>> findByCategoryAndSubcategoryAndTag(String categoryName, String subcategoryName, String tagName, int page, int size, String sortBy, String order) {

        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ProductBasicResponse> productPage = productRepository.findByCategoryAndSubcategoryAndTag(categoryName, subcategoryName, tagName, pageable);

        PaginationInfo paginationInfo = getPagination(productPage);

        SortInfo sortInfo = new SortInfo(sortBy, order);


        String message = productPage.isEmpty()
                ? ConstantGeneral.empty_list
                : ConstantProduct.success_fetch_products;

        return new MessageResponse<>(200, message, productPage.getContent(), paginationInfo, sortInfo);
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
        String key = PRODUCT_DETAIL_KEY + id;

        ProductDetailResponse cache = redisProductService.getProduct(key, ProductDetailResponse.class);
        if (cache != null) {
            return new BasicMessageResponse<>(200, null, cache);
        }

        ProductDetailResponse response = productRepository.getDetailByProductId(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));

        List<String> images = productImageRepository.findImageUrlsByProductId(response.getId());
        List<ReviewCommentDto> reviews = reviewCommentRepository.getReviewsByProductId(response.getId());
        String description = productDescriptionRepository.getDescriptionByProductId(response.getId());

        response.setImages(images);
        response.setDescription(description);
        response.setReviews(reviews);

        productStatisticService.updateViewCount(response.getId());

        redisProductService.setProduct(key, response, 6, TimeUnit.HOURS);

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

}
