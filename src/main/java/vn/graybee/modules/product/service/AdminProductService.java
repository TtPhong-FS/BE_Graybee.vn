package vn.graybee.modules.product.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.dto.MessageResponse;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.product.dto.request.ProductCreateRequest;
import vn.graybee.modules.product.dto.request.ProductUpdateRequest;
import vn.graybee.modules.product.dto.response.InventoryProductDto;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.dto.response.ProductUpdateDto;
import vn.graybee.response.admin.products.ProductStatusResponse;

import java.util.List;

public interface AdminProductService {

    BasicMessageResponse<ProductResponse> createProduct(ProductCreateRequest request);

    BasicMessageResponse<ProductResponse> updateProduct(long id, ProductUpdateRequest request);

    BasicMessageResponse<Long> deleteProductById(long id);

    MessageResponse<List<ProductResponse>> getAllProductDtoForDashboard(String status, String categoryName, String manufacturerName, int page, int size, String sortBy, String order);

    BasicMessageResponse<ProductUpdateDto> getById(long id);

    BasicMessageResponse<ProductStatusResponse> updateStatus(long id, String status);

    BasicMessageResponse<ProductResponse> restoreProduct(long id, UserDetail userDetail);

    InventoryProductDto getProductBasicDtoById(long productId);

}
