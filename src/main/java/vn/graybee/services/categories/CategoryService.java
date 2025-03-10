package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.category.CategoryProjection;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;
import vn.graybee.requests.categories.CategoryCreateRequest;
import vn.graybee.requests.categories.CategoryUpdateRequest;
import vn.graybee.response.categories.CategoryResponse;

import java.util.List;

public interface CategoryService {

    BasicMessageResponse<CategoryResponse> createCategory(CategoryCreateRequest request);

    BasicMessageResponse<CategoryResponse> updateCategory(int id, CategoryUpdateRequest request);

    BasicMessageResponse<CategoryResponse> findById(int id);

    BasicMessageResponse<Integer> deleteCategoryById(int id);


    BasicMessageResponse<List<CategoryProjection>> getCategories();

    //    public
    BasicMessageResponse<List<CategoryBasicInfoProjection>> getCategories_public();

}
