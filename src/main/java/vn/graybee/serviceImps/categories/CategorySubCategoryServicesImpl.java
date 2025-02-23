package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.repositories.categories.CategorySubCategoryRepository;
import vn.graybee.response.categories.SubCategorySummaryResponse;
import vn.graybee.services.categories.CategorySubCategoryServices;

import java.util.List;

@Service
public class CategorySubCategoryServicesImpl implements CategorySubCategoryServices {

    private final CategorySubCategoryRepository categorySubCategoryRepository;

    public CategorySubCategoryServicesImpl(CategorySubCategoryRepository categorySubCategoryRepository) {
        this.categorySubCategoryRepository = categorySubCategoryRepository;
    }

    @Override
    public BasicMessageResponse<List<SubCategorySummaryResponse>> findByCategoryId(int categoryId) {
        List<SubCategorySummaryResponse> subcategories = categorySubCategoryRepository.findByCategoryId(categoryId);

        return new BasicMessageResponse<>(200, "List subcategory owned by category id = " + categoryId + ": ", subcategories);
    }

}
