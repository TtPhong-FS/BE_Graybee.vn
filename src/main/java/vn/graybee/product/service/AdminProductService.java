package vn.graybee.product.service;

import vn.graybee.account.security.UserDetail;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.product.dto.request.ProductCreateRequest;
import vn.graybee.product.dto.request.ProductRelationUpdateRequest;
import vn.graybee.product.dto.request.ProductUpdateRequest;
import vn.graybee.product.dto.response.ProductResponse;
import vn.graybee.response.admin.products.ProductIdAndTagIdResponse;
import vn.graybee.response.admin.products.ProductStatusResponse;
import vn.graybee.response.admin.products.ProductSubcategoryAndTagResponse;
import vn.graybee.response.admin.products.ProductSubcategoryIDResponse;
import vn.graybee.response.admin.products.ProductUpdateResponse;

import java.util.List;

public interface AdminProductService {

    void checkExistById(long id);

    BasicMessageResponse<ProductResponse> create(ProductCreateRequest request);

    BasicMessageResponse<ProductResponse> update(long id, ProductUpdateRequest request);

    BasicMessageResponse<ProductSubcategoryAndTagResponse> updateSubcategoriesAndTagIds(long id, ProductRelationUpdateRequest request);

    BasicMessageResponse<Long> delete(long id);

    MessageResponse<List<ProductResponse>> fetchAll(String status, String categoryName, String manufacturerName, int page, int size, String sortBy, String order);

    BasicMessageResponse<List<ProductSubcategoryAndTagResponse>> fetchAllWithSubcategoriesAndTags();

    BasicMessageResponse<ProductUpdateResponse> getById(long id);

    BasicMessageResponse<ProductStatusResponse> updateStatus(long id, String status);

    BasicMessageResponse<ProductResponse> restoreProduct(long id, UserDetail userDetail);

    BasicMessageResponse<ProductIdAndTagIdResponse> deleteRelationByProductIdAndTagId(long productId, int tagId);

    BasicMessageResponse<ProductSubcategoryIDResponse> deleteRelationByProductIdAndSubcategoryId(long productId, int subcategoryId);

}
