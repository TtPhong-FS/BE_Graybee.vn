package vn.graybee.modules.product.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantProduct;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.common.dto.PaginationInfo;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.comment.dto.response.ReviewCommentDto;
import vn.graybee.modules.comment.repository.ReviewCommentRepository;
import vn.graybee.modules.product.dto.response.FavoriteProductResponse;
import vn.graybee.modules.product.repository.ProductDescriptionRepository;
import vn.graybee.modules.product.repository.ProductImageRepository;
import vn.graybee.modules.product.repository.ProductRepository;
import vn.graybee.modules.product.service.ProductService;
import vn.graybee.modules.product.service.RedisProductService;
import vn.graybee.modules.product.service.detail.DetailFetcher;
import vn.graybee.response.publics.products.DetailTemplateResponse;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductDetailResponse;
import vn.graybee.response.publics.products.ProductPriceResponse;

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

    private final ProductImageRepository productImageRepository;

    private final ProductDescriptionRepository productDescriptionRepository;

    private final ReviewCommentRepository reviewCommentRepository;

    private final RedisProductService redisProductService;

    public ProductServiceImpl(MessageSourceUtil messageSourceUtil, List<DetailFetcher> detailFetchers, ProductRepository productRepository, ProductImageRepository productImageRepository, ProductDescriptionRepository productDescriptionRepository, ReviewCommentRepository reviewCommentRepository, RedisProductService redisProductService) {
        this.messageSourceUtil = messageSourceUtil;
        this.detailFetchers = detailFetchers;
        this.productRepository = productRepository;
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

//        List<ProductBasicResponse> cachedProducts = redisProductService.getCachedListProductBasicBySortedSet(categoryName, sortBy, page, size, true);
//
//        if (cachedProducts != null && !cachedProducts.isEmpty()) {
//
//            long totalElements = cachedProducts.size();
//
//            int totalPages = (int) Math.ceil((double) totalElements / size);
//
//            PaginationInfo paginationInfo = new PaginationInfo(
//                    page, totalPages, totalElements, size
//            );
//
//            SortInfo sortInfo = new SortInfo(
//                    sortBy, order
//            );
//
//            return new MessageResponse<>(200, ConstantProduct.success_fetch_products, cachedProducts, paginationInfo, sortInfo);
//        }
//
//        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        Page<ProductBasicResponse> productPage = productRepository.findProductsByCategoryName(categoryName, pageable);
//
//        PaginationInfo paginationInfo = new PaginationInfo(
//                productPage.getNumber(), productPage.getTotalPages(), productPage.getTotalElements(), productPage.getSize()
//        );
//
//        SortInfo sortInfo = new SortInfo(
//                sortBy, order
//        );
//
//        String message = productPage.isEmpty()
//                ? ConstantGeneral.empty_list
//                : ConstantProduct.success_fetch_products;
//
//        redisProductService.cacheListProductBasicBySortedSet(productPage.getContent(), categoryName, sortBy, 12, TimeUnit.HOURS);
//
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
    public BigDecimal getProductPriceById(long id) {
        return productRepository.findFinalPriceById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("product.not.found.or.out_of_business")));
    }

    @Override
    public FavoriteProductResponse getProductFavouriteById(Long productId) {
        return productRepository.findProductFavouriteById(productId)
                .orElseThrow(() -> new CustomNotFoundException(ConstantGeneral.general, messageSourceUtil.get("product.not.found.or.out_of_business")));
    }

}
