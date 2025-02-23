package vn.graybee.services.products;

import vn.graybee.models.products.Product;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.response.DetailDtoResponse;

public interface ProductDetailService {

    void saveDetail(Product product, DetailDtoRequest request);

    DetailDtoResponse getDetail(Product product);

    String getDetailType();

}
