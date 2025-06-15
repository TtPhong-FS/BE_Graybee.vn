package vn.graybee.modules.product.service;

import org.springframework.data.domain.Page;
import vn.graybee.modules.product.dto.request.ProductRequest;
import vn.graybee.modules.product.dto.request.ProductUpdateRequest;
import vn.graybee.modules.product.dto.request.ValidationProductRequest;
import vn.graybee.modules.product.dto.response.InventoryProductDto;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.dto.response.ProductUpdateDto;
import vn.graybee.response.admin.products.ProductStatusResponse;

public interface AdminProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(long id, ProductUpdateRequest request);

    Long deleteProductById(long id);

    Page<ProductResponse> getAllProductDtoForDashboard(String status, String categoryName, String manufacturerName, int page, int size, String sortBy, String order);

    ProductUpdateDto getProductUpdateDtoById(long id);

    ProductStatusResponse updateStatus(long id, String status);

    InventoryProductDto getProductBasicDtoById(long productId);

    void checkBeforeCreate(Long id, ValidationProductRequest request);

}
