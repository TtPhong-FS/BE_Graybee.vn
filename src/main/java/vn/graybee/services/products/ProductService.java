package vn.graybee.services.products;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.requests.products.ProductUpdateRequest;
import vn.graybee.response.products.ProductDto;
import vn.graybee.response.products.ProductResponse;
import vn.graybee.response.products.ProductStatusResponse;
import vn.graybee.response.publics.ProductBasicResponse;
import vn.graybee.response.publics.collections.PcSummaryResponse;

import java.util.List;

public interface ProductService {

    BasicMessageResponse<ProductResponse> create(ProductCreateRequest request);

    BasicMessageResponse<ProductResponse> update(long productId, ProductUpdateRequest request);

    BasicMessageResponse<List<ProductResponse>> getProductsForAdmin();

    BasicMessageResponse<ProductDto> findById(long id);

    PcSummaryResponse findPCByCategoryName_PUBLIC(String categoryName);

    void updateProductCountCategory(int CategoryId, boolean isIncrease);

    void updateProductCountManufacturer(int ManufacturerId, boolean isIncrease);

    BasicMessageResponse<List<ProductBasicResponse>> fetchByCategoryName(String categoryName);

    BasicMessageResponse<ProductStatusResponse> updateStatus(long id, String status);

}
