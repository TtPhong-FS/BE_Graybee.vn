package vn.graybee.services;

import org.springframework.stereotype.Service;
import vn.graybee.services.business.ProductDetailService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductDetailFactory {

    private final Map<String, ProductDetailService> services = new HashMap<>();

    public ProductDetailFactory(List<ProductDetailService> servicesList) {
        for (ProductDetailService service : servicesList) {
            services.put(service.getDetailType(), service);
        }
    }

    public ProductDetailService getService(String detailType) {
        return services.get(detailType);
    }

}
