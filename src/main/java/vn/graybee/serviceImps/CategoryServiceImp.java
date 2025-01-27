package vn.graybee.serviceImps;

import org.springframework.stereotype.Service;
import vn.graybee.models.business.Category;
import vn.graybee.repositories.business.CategoryRepository;
import vn.graybee.requests.category.CategoryCreateRequest;
import vn.graybee.response.CategoryResponse;
import vn.graybee.services.business.CategoryService;
import vn.graybee.validation.CategoryValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryValidation categoryValidation;

    public CategoryServiceImp(CategoryRepository categoryRepository, CategoryValidation categoryValidation) {
        this.categoryRepository = categoryRepository;
        this.categoryValidation = categoryValidation;
    }

    @Override
    public Category insertCategory(CategoryCreateRequest request) {
        categoryValidation.ensureCategoryNameBeforeCreate(request.getName());
        Category category = new Category(
                request.getName().toUpperCase(),
                request.getIsDelete()
        );
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(long id) {
        categoryValidation.ensureExistsById(id);
        categoryValidation.checkProductExistsByCategoryId(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public void updateStatusDeleteRecord(long id) {
        Category category = categoryValidation.findToUpdateStatusDelete(id);
        if (category.getIsDelete().equals("false")) {
            category.setIsDelete("true");
        } else {
            category.setIsDelete("false");
        }
        categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Category category : categories) {
            categoryResponses.add(new CategoryResponse(category.getId(), category.getName(), category.getIsDelete()));
        }
        return categoryResponses;
    }

}
