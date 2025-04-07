package vn.graybee.services.products;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.favourites.ProductFavourite;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductDetailResponse;
import vn.graybee.response.publics.products.ProductPriceResponse;

import java.util.List;

public interface ProductService_public {

    BasicMessageResponse<List<ProductBasicResponse>> fetchProductsFromClient();

    BasicMessageResponse<List<ProductBasicResponse>> findByCategoryName(String categoryName);

    BasicMessageResponse<List<ProductBasicResponse>> findByCategoryAndManufacturer(String categoryName, String manufacturerName);

    BasicMessageResponse<List<ProductBasicResponse>> findByCategoryAndSubcategoryAndTag(String categoryName, String subcategoryName, String tagName);

    ProductPriceResponse getPriceById(long id);

    ProductBasicResponse findBasicToAddToCartById(long id);

    BasicMessageResponse<ProductDetailResponse> getDetailById(long id);

    BasicMessageResponse<List<ProductFavourite>> getFavouritesByUserUid(Integer userUid);

    BasicMessageResponse<?> addToFavourite(int userUid, long productId);

}
