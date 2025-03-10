package vn.graybee.services.products;

import vn.graybee.requests.DetailDtoRequest;

public interface ProductDetailService {

    void saveDetail(long productId, String categoryName, DetailDtoRequest request);
    
    String getDetailType();

}
