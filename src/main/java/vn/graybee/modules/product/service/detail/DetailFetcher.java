package vn.graybee.modules.product.service.detail;

import vn.graybee.response.publics.products.DetailTemplateResponse;

public interface DetailFetcher {

    String getDetailType();

    DetailTemplateResponse fetchDetail(Long productId);

}
