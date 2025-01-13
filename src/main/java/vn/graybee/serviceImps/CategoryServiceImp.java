package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import vn.graybee.models.Category;
import vn.graybee.repositories.CategoryRepository;
import vn.graybee.requests.categories.CategoryCreateRequest;
import vn.graybee.services.CategoryService;

import java.time.LocalDateTime;

@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void insertCategory(CategoryCreateRequest request) {
        Category category = new Category(
                LocalDateTime.now(),
                LocalDateTime.now(),
                request.getName()
        );
        categoryRepository.save(category);
    }

}
