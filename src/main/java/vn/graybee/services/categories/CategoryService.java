package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;
import vn.graybee.requests.directories.CategoryCreateRequest;
import vn.graybee.requests.directories.CategoryUpdateRequest;
import vn.graybee.response.admin.directories.category.CategoryResponse;

import java.util.List;

public interface CategoryService {

    BasicMessageResponse<CategoryResponse> createCategory(CategoryCreateRequest request);

    BasicMessageResponse<CategoryResponse> updateCategory(int id, CategoryUpdateRequest request);

    BasicMessageResponse<CategoryResponse> findById(int id);

    BasicMessageResponse<Integer> deleteCategoryById(int id);

    BasicMessageResponse<List<CategoryResponse>> fetchCategories_ADMIN();

    //    public
    BasicMessageResponse<List<CategoryBasicInfoProjection>> getCategories_public();

}
