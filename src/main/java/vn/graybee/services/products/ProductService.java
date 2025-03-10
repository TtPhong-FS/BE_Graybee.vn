package vn.graybee.services.products;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.products.Product;
import vn.graybee.projections.product.ProductProjection;
import vn.graybee.requests.products.ProductCreateRequest;
import vn.graybee.response.products.ProductResponse;
import vn.graybee.response.publics.collections.PcSummaryResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    BasicMessageResponse<ProductResponse> create(ProductCreateRequest request);

    BasicMessageResponse<List<ProductProjection>> fetchAll();

    Optional<Product> getById(long id);

    PcSummaryResponse findPCByCategoryName_PUBLIC(String categoryName);

    void updateProductCountCategory(int CategoryId, boolean isIncrease);

    void updateProductCountManufacturer(int ManufacturerId, boolean isIncrease);

}
