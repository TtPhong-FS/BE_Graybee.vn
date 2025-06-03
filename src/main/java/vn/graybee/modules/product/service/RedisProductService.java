package vn.graybee.modules.product.service;

import vn.graybee.response.publics.products.ProductBasicResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface RedisProductService {

    <T> T getProduct(String key, Class<T> clazz);

    <T> void setProduct(String key, T value, long timeout, TimeUnit unit);

    void deleteProduct(String key);

    void deleteProductListPattern(String category);

    void cacheListProductBasicBySortedSet(List<ProductBasicResponse> products, String categoryName, String sortBy, long timeout, TimeUnit unit);

    List<ProductBasicResponse> getCachedListProductBasicBySortedSet(String categoryName, String sortBy, int page, int size, boolean asc);

}
