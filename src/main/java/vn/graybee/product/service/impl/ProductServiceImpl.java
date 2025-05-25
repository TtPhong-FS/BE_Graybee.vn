package vn.graybee.product.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.comment.repository.ReviewCommentRepository;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantProduct;
import vn.graybee.common.constants.ConstantUser;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.common.dto.PaginationInfo;
import vn.graybee.common.dto.SortInfo;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.inventory.dto.response.InventoryProductResponse;
import vn.graybee.product.model.Favourite;
import vn.graybee.product.repository.FavouriteRepository;
import vn.graybee.product.repository.ProductDescriptionRepository;
import vn.graybee.product.repository.ProductImageRepository;
import vn.graybee.product.repository.ProductRepository;
import vn.graybee.product.service.ProductService;
import vn.graybee.product.service.RedisProductService;
import vn.graybee.product.service.detail.DetailFetcher;
import vn.graybee.response.favourites.ProductFavourite;
import vn.graybee.response.publics.products.DetailTemplateResponse;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductDetailResponse;
import vn.graybee.response.publics.products.ProductPriceResponse;
import vn.graybee.response.publics.products.ReviewCommentDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_DETAIL_KEY = "product:detail:";

    private static final String PRODUCT_LIST_KEY = "product:list:";

    private final MessageSourceUtil messageSourceUtil;

    private final List<DetailFetcher> detailFetchers;

    private final ProductRepository productRepository;

    private final FavouriteRepository favouriteRepository;

    private final ProductImageRepository productImageRepository;

    private final ProductDescriptionRepository productDescriptionRepository;

    private final ReviewCommentRepository reviewCommentRepository;

    private final RedisProductService redisProductService;

    public ProductServiceImpl(MessageSourceUtil messageSourceUtil, List<DetailFetcher> detailFetchers, ProductRepository productRepository, FavouriteRepository favouriteRepository, ProductImageRepository productImageRepository, ProductDescriptionRepository productDescriptionRepository, ReviewCommentRepository reviewCommentRepository, RedisProductService redisProductService) {
        this.messageSourceUtil = messageSourceUtil;
        this.detailFetchers = detailFetchers;
        this.productRepository = productRepository;
        this.favouriteRepository = favouriteRepository;
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

//        Page<ProductBasicResponse> productPage = productRepository.findByCategoryAndSubcategoryAndTag(categoryName, subcategoryName, tagName, pageable);
//
//        PaginationInfo paginationInfo = getPagination(productPage);
//
//        SortInfo sortInfo = new SortInfo(sortBy, order);
//
//
//        String message = productPage.isEmpty()
//                ? ConstantGeneral.empty_list
//                : ConstantProduct.success_fetch_products;

//        return new MessageResponse<>(200, message, productPage.getContent(), paginationInfo, sortInfo);
        return null;
    }

    @Override
    public ProductPriceResponse getPriceById(long id) {
        return productRepository.getPriceById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));
    }

    @Override
    public ProductBasicResponse getProductBasicInfoForCart(long id) {
        return productRepository.findBasicInfoForCart(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantProduct.does_not_exists));
    }

    @Override
    public InventoryProductResponse getInventoryProductResponseById(long id) {
        return productRepository.findBasicInfoForInventory(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantProduct.product.toLowerCase(), messageSourceUtil.get("common.not_found", new Object[]{ConstantProduct.product})));
    }

    @Override
    public BasicMessageResponse<ProductDetailResponse> getDetailById(long id) {
        String key = PRODUCT_DETAIL_KEY + id;

        ProductDetailResponse cache = redisProductService.getProduct(key, ProductDetailResponse.class);
        if (cache != null) {
            return new BasicMessageResponse<>(200, null, cache);
        }

        ProductDetailResponse productResponse = productRepository.getDetailByProductId(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, ConstantProduct.not_available));

        String type = detailFetchers.stream()
                .map(DetailFetcher::getDetailType)
                .filter(detailType -> detailType.equalsIgnoreCase(productResponse.getCategoryName()))
                .findFirst()
                .orElse(null);

        if (type != null) {

            DetailFetcher fetcher = detailFetchers.stream()
                    .filter(f -> f.getDetailType().equalsIgnoreCase(type))
                    .findFirst()
                    .orElse(null);

            if (fetcher != null) {
                DetailTemplateResponse detail = fetcher.fetchDetail(productResponse.getId());
                productResponse.setDetail(detail);
            }
        }

        List<String> images = productImageRepository.findImageUrlsByProductId(productResponse.getId());
        List<ReviewCommentDto> reviews = reviewCommentRepository.getReviewsByProductId(productResponse.getId());
        String description = productDescriptionRepository.getDescriptionByProductId(productResponse.getId());

        productResponse.setImages(images);
        productResponse.setDescription(description);
        productResponse.setReviews(reviews);

//        productStatisticService.updateViewCount(productResponse.getId());

        redisProductService.setProduct(key, productResponse, 6, TimeUnit.HOURS);

        return new BasicMessageResponse<>(200, null, productResponse);
    }

    @Override
    public BasicMessageResponse<List<ProductFavourite>> getFavouritesByUserUid(String userUid) {

        List<ProductFavourite> productFavourites = favouriteRepository.getProductFavouritesByUserUId(userUid);

        if (productFavourites.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantUser.empty_favourites, productFavourites);
        }

        return new BasicMessageResponse<>(200, ConstantUser.success_get_favourites, productFavourites);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<?> addToFavourite(String userUid, long productId) {

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
    public BigDecimal getProductPriceById(long id) {
        return productRepository.findFinalPriceById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("product.not.found.or.out_of_business")));
    }

}
