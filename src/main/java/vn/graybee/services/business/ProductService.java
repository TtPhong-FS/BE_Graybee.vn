package vn.graybee.services.business;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.products.Product;
import vn.graybee.requests.ProductCreateRequest;
import vn.graybee.response.ProductResponseByCategoryName;
import vn.graybee.response.products.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    BasicMessageResponse<ProductResponse> createProduct(ProductCreateRequest request);

    Optional<Product> getById(Long id);

    List<ProductResponseByCategoryName> findProductsByCategoryName(String name);

}
