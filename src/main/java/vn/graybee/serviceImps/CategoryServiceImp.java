package vn.graybee.serviceImps;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.graybee.messages.MessageResponse;
import vn.graybee.messages.other.PaginationInfo;
import vn.graybee.messages.other.SortInfo;
import vn.graybee.models.business.Category;
import vn.graybee.projections.CategoryProjection;
import vn.graybee.repositories.business.CategoryRepository;
import vn.graybee.requests.category.CategoryCreateRequest;
import vn.graybee.response.CategoryResponse;
import vn.graybee.services.business.CategoryService;
import vn.graybee.utils.TextUtils;
import vn.graybee.validation.CategoryValidation;

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
    public CategoryResponse insertCategory(CategoryCreateRequest request) {
        categoryValidation.checkCategoryNameExists(request.getCategoryName());
        Category category = new Category(
                TextUtils.capitalizeEachWord(request.getCategoryName())
        );
        category.setDeleted(false);
        Category savedCategory = categoryRepository.save(category);

        return new CategoryResponse(savedCategory.getId(), savedCategory.getCategoryName(),
                savedCategory.isDeleted(),
                savedCategory.getCreatedAt(),
                savedCategory.getUpdatedAt());
    }

    @Override
    public void deleteCategoryById(long id) {
        categoryValidation.checkExists(id);
        categoryValidation.checkProductExists(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public void updateStatusDeleteRecord(long id) {
        Category category = categoryValidation.findToUpdateStatusDelete(id);
        category.setDeleted(!category.isDeleted());
        categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public MessageResponse<List<CategoryResponse>> getCategories(int page, int size, String sortBy, String order) {

        Sort.Direction direction = order.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));

        Page<CategoryProjection> categoryPage = categoryRepository.findAllCategories(pageable);


        List<CategoryResponse> categoryResponses = categoryPage.getContent()
                .stream()
                .map(c -> new CategoryResponse(c.getId(), c.getCategoryName(), c.isDeleted(), c.getCreatedAt(), c.getUpdatedAt()))
                .toList();

        PaginationInfo paginationInfo = new PaginationInfo(categoryPage.getNumber() + 1, categoryPage.getTotalPages(),
                categoryPage.getTotalElements(), categoryPage.getSize());

        SortInfo sortInfo = new SortInfo(sortBy, order);

        return new MessageResponse<>(200, "List categories: ", categoryResponses, paginationInfo, sortInfo);
    }

}
