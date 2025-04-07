package vn.graybee.services.products;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.response.admin.products.ProductStatisticResponse;

import java.util.List;

public interface ProductStatisticService {

    BasicMessageResponse<List<ProductStatisticResponse>> fetchAll();

    void updateViewCount(long productId);

}
