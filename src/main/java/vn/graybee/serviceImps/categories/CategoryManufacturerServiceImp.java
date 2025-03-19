package vn.graybee.serviceImps.categories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.repositories.categories.CategoryManufacturerRepository;
import vn.graybee.response.categories.CategoryWithManufacturersResponse;
import vn.graybee.response.categories.ManufacturerSummaryResponse;
import vn.graybee.services.categories.CategoryManufacturerService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CategoryManufacturerServiceImp implements CategoryManufacturerService {

    private static final String CACHE_KEY = "manufacturers:categoryId:";

    private final CategoryManufacturerRepository cmRepository;

    private final StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper;

    public CategoryManufacturerServiceImp(CategoryManufacturerRepository cmRepository, StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.cmRepository = cmRepository;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public BasicMessageResponse<List<CategoryWithManufacturersResponse>> fetchAll() {
        try {
            List<CategoryWithManufacturersResponse> responses = loadFromCache();
            if (responses != null) {
                return new BasicMessageResponse<>(200, "Lấy danh sách từ cache thành công!", responses);
            }

            responses = loadFromDatabase();
            saveToCache(responses);

            return new BasicMessageResponse<>(200, "Lấy danh sách thành công!", responses);
        } catch (Exception e) {
            return new BasicMessageResponse<>(500, "Lỗi khi lấy danh sách!", null);
        }
    }

    @Override
    public BasicMessageResponse<Integer> deleteManufacturerByIdAndCategoryById(int manufacturerId, int categoryId) {

        cmRepository.deleteManufacturerByIdAndCategoryById(manufacturerId, categoryId);
        
        return new BasicMessageResponse<>(200, "Xoá quan hệ thành công!", manufacturerId);
    }

    /**
     * Tải danh sách từ Redis
     */
    private List<CategoryWithManufacturersResponse> loadFromCache() throws JsonProcessingException {
        Set<String> cachedKeys = redisTemplate.keys(CACHE_KEY + "*");
        if (cachedKeys == null || cachedKeys.isEmpty()) {
            return null;
        }

        List<CategoryWithManufacturersResponse> cachedList = new ArrayList<>();
        for (String key : cachedKeys) {
            String json = redisTemplate.opsForValue().get(key);
            if (json != null) {
                cachedList.add(objectMapper.readValue(json, CategoryWithManufacturersResponse.class));
            }
        }
        return cachedList.isEmpty() ? null : cachedList;
    }

    /**
     * Tải danh sách từ Database
     */
    private List<CategoryWithManufacturersResponse> loadFromDatabase() {

        List<Object[]> rawData = cmRepository.fetchCategoryWithManufacturersRaw();
        Map<Integer, CategoryWithManufacturersResponse> responseMap = new LinkedHashMap<>();

        for (Object[] row : rawData) {
            Integer categoryId = (Integer) row[0];
            String categoryName = (String) row[1];
            Integer manufacturerId = (Integer) row[2];
            String manufacturerName = (String) row[3];

            responseMap.computeIfAbsent(categoryId, id ->
                    new CategoryWithManufacturersResponse(id, categoryName, new ArrayList<>()));

            responseMap.get(categoryId).getManufacturers().add(
                    new ManufacturerSummaryResponse(manufacturerId, manufacturerName)
            );
        }

        return new ArrayList<>(responseMap.values());
    }

    /**
     * Lưu danh sách vào Redis
     */
    private void saveToCache(List<CategoryWithManufacturersResponse> responses) throws JsonProcessingException {
        for (CategoryWithManufacturersResponse category : responses) {
            String key = CACHE_KEY + category.getId();
            String jsonData = objectMapper.writeValueAsString(category);
            redisTemplate.opsForValue().set(key, jsonData, Duration.ofMinutes(1440));
        }
    }


}
