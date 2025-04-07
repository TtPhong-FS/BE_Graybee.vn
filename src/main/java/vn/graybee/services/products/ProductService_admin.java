package vn.graybee.services.products;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.requests.products.ProductRelationUpdateRequest;
import vn.graybee.requests.products.ProductUpdateRequest;
import vn.graybee.response.admin.products.ProductDto;
import vn.graybee.response.admin.products.ProductIdAndTagIdResponse;
import vn.graybee.response.admin.products.ProductResponse;
import vn.graybee.response.admin.products.ProductStatusResponse;
import vn.graybee.response.admin.products.ProductSubcategoryAndTagResponse;
import vn.graybee.response.admin.products.ProductSubcategoryIDResponse;

import java.util.List;

public interface ProductService_admin {

    BasicMessageResponse<ProductResponse> create(ProductCreateRequest request);

    BasicMessageResponse<ProductResponse> update(long id, ProductUpdateRequest request);

    BasicMessageResponse<ProductSubcategoryAndTagResponse> updateSubcategoriesAndTagIds(long id, ProductRelationUpdateRequest request);

    BasicMessageResponse<Long> delete(long id);

    BasicMessageResponse<List<ProductResponse>> fetchAll();

    BasicMessageResponse<List<ProductSubcategoryAndTagResponse>> fetchAllWithSubcategoriesAndTags();

    BasicMessageResponse<ProductDto> getById(long id);

    void updateProductCountCategory(int CategoryId, boolean isIncrease);

    void updateProductCountManufacturer(int ManufacturerId, boolean isIncrease);

    BasicMessageResponse<ProductStatusResponse> updateStatus(long id, String status);

    BasicMessageResponse<ProductIdAndTagIdResponse> deleteRelationByProductIdAndTagId(long productId, int tagId);

    BasicMessageResponse<ProductSubcategoryIDResponse> deleteRelationByProductIdAndSubcategoryId(long productId, int subcategoryId);

}
