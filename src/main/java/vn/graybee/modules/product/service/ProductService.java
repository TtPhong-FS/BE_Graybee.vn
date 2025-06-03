package vn.graybee.modules.product.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.modules.product.dto.response.FavoriteProductResponse;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductDetailResponse;
import vn.graybee.response.publics.products.ProductPriceResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    MessageResponse<List<ProductBasicResponse>> findByCategoryName(String categoryName, int page, int size, String sortBy, String order);

    ProductPriceResponse getPriceById(long id);

    ProductBasicResponse getProductBasicInfoForCart(long id);

    BasicMessageResponse<ProductDetailResponse> getDetailById(long id);

    BigDecimal getProductPriceById(long id);

    FavoriteProductResponse getProductFavouriteById(Long productId);

}
