package vn.graybee.services.business;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.messages.MessageResponse;
import vn.graybee.models.categories.Category;
import vn.graybee.projections.CategoryProjection;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;
import vn.graybee.requests.category.CategoryCreateRequest;
import vn.graybee.response.CategoryResponse;

import java.util.List;

public interface CategoryService {


    CategoryResponse insertCategory(CategoryCreateRequest request);

    BasicMessageResponse<Integer> deleteCategoryById(int id);

    void updateStatusDeleteRecord(int id);

    Category findById(int id);

    MessageResponse<List<CategoryProjection>> getCategories(int page, int size, String sortBy, String order);

    //    public
    BasicMessageResponse<List<CategoryBasicInfoProjection>> getCategories_public();

}
