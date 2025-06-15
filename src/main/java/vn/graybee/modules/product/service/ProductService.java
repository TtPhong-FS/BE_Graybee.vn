package vn.graybee.modules.product.service;

import vn.graybee.modules.account.dto.response.FavoriteProductResponse;
import vn.graybee.modules.product.dto.response.ProductBasicResponse;
import vn.graybee.modules.product.dto.response.ProductDetailDto;
import vn.graybee.response.publics.products.ProductPriceResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductBasicResponse> findProductByCategorySlug(String categorySlug);

    ProductPriceResponse getPriceById(long id);

    ProductBasicResponse getProductBasicInfoForCart(long id);

    ProductDetailDto findProductDetailBySlug(String slug);

    BigDecimal getProductPriceById(long id);

    FavoriteProductResponse getProductFavouriteById(Long productId);

    List<ProductBasicResponse> getAllProductPublished();

}
