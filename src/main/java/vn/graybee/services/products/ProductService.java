package vn.graybee.services.products;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.products.Product;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.response.products.ProductResponse;
import vn.graybee.response.publics.collections.PcSummaryResponse;

import java.util.Optional;

public interface ProductService {

    BasicMessageResponse<ProductResponse> createProduct(ProductCreateRequest request);

    Optional<Product> getById(long id);

    PcSummaryResponse findPCByCategoryName_PUBLIC(String categoryName);

}
