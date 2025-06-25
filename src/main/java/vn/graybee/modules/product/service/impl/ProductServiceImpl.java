package vn.graybee.modules.product.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.graybee.common.Constants;
import vn.graybee.common.dto.PaginationInfo;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.modules.account.dto.response.FavoriteProductResponse;
import vn.graybee.modules.catalog.dto.response.attribute.AttributeDisplayDto;
import vn.graybee.modules.catalog.enums.CategoryType;
import vn.graybee.modules.comment.dto.response.ReviewCommentDto;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;
import vn.graybee.modules.product.dto.response.ProductDetailDto;
import vn.graybee.modules.product.repository.ProductRepository;
import vn.graybee.modules.product.service.ProductAttributeValueService;
import vn.graybee.modules.product.service.ProductCategoryService;
import vn.graybee.modules.product.service.ProductImageService;
import vn.graybee.modules.product.service.ProductService;
import vn.graybee.modules.product.service.RedisProductService;
import vn.graybee.modules.product.service.ReviewCommentSerivce;
import vn.graybee.response.publics.products.ProductPriceResponse;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_DETAIL_KEY = "product:detail:";

    private static final String PRODUCT_LIST_KEY = "product:list:";

    private final MessageSourceUtil messageSourceUtil;

    private final ProductRepository productRepository;

    private final ProductImageService productImageService;

    private final ProductAttributeValueService productAttributeValueService;

    private final ReviewCommentSerivce reviewCommentSerivce;

    private final ProductCategoryService productCategoryService;

    private final RedisProductService redisProductService;


    public PaginationInfo getPagination(Page<ProductBasicResponse> page) {
        return new PaginationInfo(
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize()
        );
    }


    @Override
    public List<ProductBasicResponse> findProductByCategorySlug(String categorySlug) {
        return null;
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

    }


    @Override
    public ProductPriceResponse getPriceById(long id) {
        return productRepository.getPriceById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, "Sản phẩm không tồn tại hoặc đã ngừng kinh doanh"));
    }

    @Override
    public ProductBasicResponse getProductBasicInfoForCart(long id) {
        return productRepository.findBasicInfoForCart(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, "Sản phẩm không tồn tại hoặc đã ngừng kinh doanh"));
    }

    @Override
    public ProductDetailDto findProductDetailBySlug(String slug) {

        ProductDetailDto productDetailDto = productRepository.findProductDetailDtoBySlug(slug)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("product.not.found")));

        List<AttributeDisplayDto> attributeDisplayDtos = productAttributeValueService.getAttributeDisplayDtosByProductId(productDetailDto.getProductId());

        List<ReviewCommentDto> reviewCommentDtos = reviewCommentSerivce.getReviewCommentDtosByProductId(productDetailDto.getProductId());

        productDetailDto.setReviews(reviewCommentDtos);
        productDetailDto.setSpecifications(attributeDisplayDtos);
        return productDetailDto;
    }

    @Override
    public Double getProductPriceById(long id) {
        return productRepository.findFinalPriceById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("product.not.found.or.out_of_business")));
    }

    @Override
    public FavoriteProductResponse getProductFavouriteById(Long productId) {
        return productRepository.findProductFavouriteById(productId)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("product.not.found.or.out_of_business")));
    }

    @Override
    public List<ProductBasicResponse> getAllProductPublished() {
        return productRepository.findAllProductPublished();
    }

    @Override
    public void checkExistsById(long id) {
        if (!productRepository.checkExistsById(id)) {
            throw new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("product.not.found.or.out_of_business"));
        }
    }

    @Override
    public long getIdBySlug(String productSlug) {
        return productRepository.findIdBySlug(productSlug).orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, messageSourceUtil.get("product.not.found.or.out_of_business")));
    }

    @Override
    public Page<ProductBasicResponse> getProductByCategory(String category, int page, int size, String sortBy, String order) {

        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findByCategoryName(category, pageable);
    }

    @Override
    public List<ProductBasicResponse> findProductByCategorySlugAndCategoryType(String slug, String type) {

        CategoryType categoryType = CategoryType.getType(type, messageSourceUtil);

        return switch (categoryType) {
            case CATEGORY -> productRepository.findProductByCategorySlug(slug);
            case BRAND -> productRepository.findProductByBrandSlug(slug);
            case TAG -> productCategoryService.findProductByTagSlug(slug);
            default -> Collections.emptyList();
        };

    }

    @Override
    public List<ProductBasicResponse> carouselTopTenProductBestSellByCategory(String category) {

        Sort sort = Sort.by("updatedAt").descending();

        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<ProductBasicResponse> productBasicResponses = productRepository.findTopTenProductByCategory(category, pageable);

        return productBasicResponses.getContent();
    }

}
