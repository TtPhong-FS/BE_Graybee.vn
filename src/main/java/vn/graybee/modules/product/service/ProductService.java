package vn.graybee.modules.product.service;

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

    List<ProductBasicResponse> getProductByCategory(String category);

    List<ProductBasicResponse> findProductByBrandSlug(String brandSlug);

    List<ProductBasicResponse> findProductByCategorySlugAndCategoryType(String slug, String type);

}
