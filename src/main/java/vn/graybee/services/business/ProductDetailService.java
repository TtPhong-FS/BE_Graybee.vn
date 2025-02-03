package vn.graybee.services.business;

import vn.graybee.models.business.Product;
import vn.graybee.requests.DetailDtoRequest;

public interface ProductDetailService {

    void saveDetail(Product product, DetailDtoRequest request);

    String getDetailType();

}
