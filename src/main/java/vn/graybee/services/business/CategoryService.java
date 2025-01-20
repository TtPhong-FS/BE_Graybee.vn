package vn.graybee.services.business;

import vn.graybee.models.business.Category;
import vn.graybee.requests.category.CategoryCreateRequest;
import vn.graybee.response.CategoryResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    void insertCategory(CategoryCreateRequest request);

    Optional<Category> findById(Long id);

    List<CategoryResponse> getCategories();

}
