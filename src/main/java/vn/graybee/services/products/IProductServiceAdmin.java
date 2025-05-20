package vn.graybee.services.products;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.models.users.UserPrincipal;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.requests.products.ProductRelationUpdateRequest;
import vn.graybee.requests.products.ProductUpdateRequest;
import vn.graybee.response.admin.products.ProductIdAndTagIdResponse;
import vn.graybee.response.admin.products.ProductResponse;
import vn.graybee.response.admin.products.ProductStatusResponse;
import vn.graybee.response.admin.products.ProductSubcategoryAndTagResponse;
import vn.graybee.response.admin.products.ProductSubcategoryIDResponse;
import vn.graybee.response.admin.products.ProductUpdateResponse;

import java.util.List;

public interface IProductServiceAdmin {

    void checkExistById(long id);

    BasicMessageResponse<ProductResponse> create(ProductCreateRequest request);

    BasicMessageResponse<ProductResponse> update(long id, ProductUpdateRequest request);

    BasicMessageResponse<ProductSubcategoryAndTagResponse> updateSubcategoriesAndTagIds(long id, ProductRelationUpdateRequest request);

    BasicMessageResponse<Long> delete(long id);

    MessageResponse<List<ProductResponse>> fetchAll(String status, String categoryName, String manufacturerName, int page, int size, String sortBy, String order);

    BasicMessageResponse<List<ProductSubcategoryAndTagResponse>> fetchAllWithSubcategoriesAndTags();

    BasicMessageResponse<ProductUpdateResponse> getById(long id);

    BasicMessageResponse<ProductStatusResponse> updateStatus(long id, String status);

    BasicMessageResponse<ProductResponse> restoreProduct(long id, UserPrincipal userPrincipal);

    BasicMessageResponse<ProductIdAndTagIdResponse> deleteRelationByProductIdAndTagId(long productId, int tagId);

    BasicMessageResponse<ProductSubcategoryIDResponse> deleteRelationByProductIdAndSubcategoryId(long productId, int subcategoryId);

}
