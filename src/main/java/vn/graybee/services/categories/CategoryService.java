package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Category;
import vn.graybee.projections.CategoryProjection;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;
import vn.graybee.requests.categories.CategoryCreateRequest;
import vn.graybee.response.categories.CategoryResponse;
import vn.graybee.response.categories.CategoryStatusResponse;

import java.util.List;

public interface CategoryService {


    BasicMessageResponse<CategoryResponse> createCategory(CategoryCreateRequest request);

    BasicMessageResponse<Integer> deleteCategoryById(int id);

    BasicMessageResponse<CategoryStatusResponse> updateStatusDeleteRecord(int id);

    BasicMessageResponse<CategoryResponse> createCategoryWithSubCategory(CategoryCreateRequest request);

    BasicMessageResponse<Category> getCategoryById(int id);

    BasicMessageResponse<List<CategoryProjection>> getCategories();

    //    public
    BasicMessageResponse<List<CategoryBasicInfoProjection>> getCategories_public();

}
