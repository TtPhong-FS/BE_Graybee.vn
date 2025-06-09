package vn.graybee.modules.product.service;

import org.springframework.data.domain.Page;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.product.dto.request.ProductRequest;
import vn.graybee.modules.product.dto.request.ProductUpdateRequest;
import vn.graybee.modules.product.dto.response.InventoryProductDto;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.dto.response.ProductUpdateDto;
import vn.graybee.modules.product.dto.response.ProductWithClassifyDto;
import vn.graybee.response.admin.products.ProductStatusResponse;

public interface AdminProductService {

    ProductWithClassifyDto createProduct(ProductRequest request);

    ProductWithClassifyDto updateProduct(long id, ProductUpdateRequest request);

    Long deleteProductById(long id);

    Page<ProductResponse> getAllProductDtoForDashboard(String status, String categoryName, String manufacturerName, int page, int size, String sortBy, String order);

    ProductUpdateDto getById(long id);

    ProductStatusResponse updateStatus(long id, String status);

    ProductWithClassifyDto restoreProduct(long id, UserDetail userDetail);

    InventoryProductDto getProductBasicDtoById(long productId);

}
