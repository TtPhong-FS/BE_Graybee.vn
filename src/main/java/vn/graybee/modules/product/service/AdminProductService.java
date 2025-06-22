package vn.graybee.modules.product.service;

import vn.graybee.modules.product.dto.request.ProductRequest;
import vn.graybee.modules.product.dto.request.ProductUpdateRequest;
import vn.graybee.modules.product.dto.request.ValidationProductRequest;
import vn.graybee.modules.product.dto.response.InventoryProductDto;
import vn.graybee.modules.product.dto.response.ProductDetailDto;
import vn.graybee.modules.product.dto.response.ProductResponse;
import vn.graybee.modules.product.dto.response.ProductUpdateDto;
import vn.graybee.response.admin.products.ProductStatusResponse;

import java.util.List;

public interface AdminProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(long id, ProductUpdateRequest request);

    Long deleteProductById(long id);

    List<ProductResponse> getAllProductDtoForDashboard();

    ProductUpdateDto getProductUpdateDtoById(long id);

    ProductStatusResponse updateStatus(long id, String status);

    InventoryProductDto getProductBasicDtoById(long productId);

    void checkBeforeCreate(Long id, ValidationProductRequest request);

    ProductDetailDto getProductDetailById(long id);

}
