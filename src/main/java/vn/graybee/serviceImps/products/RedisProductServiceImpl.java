package vn.graybee.serviceImps.products;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.graybee.response.publics.products.ProductBasicResponse;
import vn.graybee.services.products.RedisProductService;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisProductServiceImpl implements RedisProductService {


    private final RedisTemplate<String, Object> redisTemplate;

    public RedisProductServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <T> T getProduct(String key, Class<T> clazz) {
        Object detail = redisTemplate.opsForValue().get(key);
        return clazz.cast(detail);

    }

    @Override
    public <T> void setProduct(String key, T value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);

    }

    @Override
    public void deleteProduct(String key) {

        redisTemplate.delete(key);
    }

    @Override
    public void cacheListProductBasicBySortedSet(List<ProductBasicResponse> products, String categoryName, String sortBy, long timeout, TimeUnit unit) {
        String key = generateKeyCategory(categoryName, sortBy);
        for (ProductBasicResponse product : products) {
            double score = product.getFinalPrice().doubleValue();
            redisTemplate.opsForZSet().add(key, product, score);
        }
        redisTemplate.expire(key, timeout, unit);
      
    }

    @Override
    public List<ProductBasicResponse> getCachedListProductBasicBySortedSet(String categoryName, String sortBy, int page, int size, boolean asc) {
        String key = generateKeyCategory(categoryName, sortBy);
        int start = page * size;
        int end = start + size - 1;

        Set<Object> cachedSet = asc
                ? redisTemplate.opsForZSet().reverseRange(key, start, end)
                : redisTemplate.opsForZSet().range(key, start, end);

        return cachedSet == null ? Collections.emptyList()
                : cachedSet.stream()
                .map(obj -> (ProductBasicResponse) obj)
                .toList();
    }

    private String generateKeyCategory(String category, String sortBy) {
        return "product:list:category:" + category + ":" + sortBy;
    }

}
