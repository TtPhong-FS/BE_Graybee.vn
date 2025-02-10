package vn.graybee.services.business;

import vn.graybee.messages.MessageResponse;
import vn.graybee.models.business.Category;
import vn.graybee.requests.category.CategoryCreateRequest;
import vn.graybee.response.CategoryResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryResponse insertCategory(CategoryCreateRequest request);

    void deleteCategoryById(long id);

    void updateStatusDeleteRecord(long id);

    Optional<Category> findById(Long id);

    MessageResponse<List<CategoryResponse>> getCategories(int page, int size, String sortBy, String order);

}
