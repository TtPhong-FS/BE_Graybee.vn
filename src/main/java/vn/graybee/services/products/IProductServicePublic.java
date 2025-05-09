package vn.graybee.services.products;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.response.favourites.ProductFavourite;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.response.publics.products.ProductDetailResponse;
import vn.graybee.response.publics.products.ProductPriceResponse;

import java.util.List;

public interface IProductServicePublic {

    MessageResponse<List<ProductBasicResponse>> findByCategoryName(String categoryName, int page, int size, String sortBy, String order);

    MessageResponse<List<ProductBasicResponse>> findByCategoryAndManufacturer(String categoryName, String manufacturerName, int page, int size, String sortBy, String order);

    MessageResponse<List<ProductBasicResponse>> findByCategoryAndSubcategoryAndTag(String categoryName, String subcategoryName, String tagName, int page, int size, String sortBy, String order);

    ProductPriceResponse getPriceById(long id);

    ProductBasicResponse findBasicToAddToCartById(long id);

    BasicMessageResponse<ProductDetailResponse> getDetailById(long id);

    BasicMessageResponse<List<ProductFavourite>> getFavouritesByUserUid(Integer userUid);

    BasicMessageResponse<?> addToFavourite(int userUid, long productId);

}
