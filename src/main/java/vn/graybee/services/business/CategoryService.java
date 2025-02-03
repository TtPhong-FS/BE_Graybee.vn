package vn.graybee.services.business;

import vn.graybee.models.business.Category;
import vn.graybee.requests.category.CategoryCreateRequest;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category insertCategory(CategoryCreateRequest request);

    void deleteCategoryById(long id);

    void updateStatusDeleteRecord(long id);

    Optional<Category> findById(Long id);

    List<Category> getCategories();

}
