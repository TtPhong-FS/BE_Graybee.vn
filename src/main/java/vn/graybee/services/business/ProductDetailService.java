package vn.graybee.services.business;

import vn.graybee.models.products.Product;
import vn.graybee.requests.DetailDtoRequest;
import vn.graybee.requests.DetailDtoResponse;

public interface ProductDetailService {

    void saveDetail(Product product, DetailDtoRequest request);

    DetailDtoResponse getDetail(Product product);

    String getDetailType();

}
