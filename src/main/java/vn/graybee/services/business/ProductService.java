package vn.graybee.services.business;

import vn.graybee.models.business.Product;
import vn.graybee.requests.product.ProductCreateRequest;
import vn.graybee.response.ProductResponseByCategoryName;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct(ProductCreateRequest request);

    Optional<Product> getById(Long id);

    List<ProductResponseByCategoryName> findProductsByCategoryName(String name);

}
