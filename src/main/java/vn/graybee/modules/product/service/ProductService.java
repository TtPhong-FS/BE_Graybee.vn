package vn.graybee.modules.product.service;

import org.springframework.data.domain.Page;
import vn.graybee.modules.account.dto.response.FavoriteProductResponse;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;
import vn.graybee.modules.product.dto.response.ProductDetailDto;
import vn.graybee.response.publics.products.ProductPriceResponse;

import java.util.List;

public interface ProductService {

    List<ProductBasicResponse> findProductByCategorySlug(String categorySlug);

    ProductPriceResponse getPriceById(long id);

    ProductBasicResponse getProductBasicInfoForCart(long id);

    ProductDetailDto findProductDetailBySlug(String slug);

    Double getProductPriceById(long id);

    FavoriteProductResponse getProductFavouriteById(Long productId);

    List<ProductBasicResponse> getAllProductPublished();

    void checkExistsById(long id);

    long getIdBySlug(String productSlug);

    Page<ProductBasicResponse> getProductByCategory(String category, int page, int size, String sortBy, String order);

    List<ProductBasicResponse> findProductByCategorySlugAndCategoryType(String slug, String type);

    List<ProductBasicResponse> carouselTopTenProductBestSellByCategory(String category);

}
