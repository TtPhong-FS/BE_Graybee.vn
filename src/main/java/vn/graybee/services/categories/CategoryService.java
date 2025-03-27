package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.publics.CategoryBasicInfoProjection;
import vn.graybee.requests.directories.CategoryCreateRequest;
import vn.graybee.requests.directories.CategoryUpdateRequest;
import vn.graybee.response.admin.directories.category.CategoryManufacturerIdResponse;
import vn.graybee.response.admin.directories.category.CategoryResponse;
import vn.graybee.response.admin.directories.category.CategorySubcategoryIdResponse;

import java.util.List;

public interface CategoryService {

    BasicMessageResponse<CategoryResponse> create(CategoryCreateRequest request);

    BasicMessageResponse<CategoryResponse> update(int id, CategoryUpdateRequest request);

    BasicMessageResponse<CategoryResponse> findById(int id);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<List<CategoryResponse>> fetchCategories_ADMIN();

    BasicMessageResponse<CategoryManufacturerIdResponse> deleteRelationByCategoryIdAndManufacturerId(int categoryId, int manufacturerId);

    BasicMessageResponse<CategorySubcategoryIdResponse> deleteRelationBySubcategoryByCategoryId(int categoryId, int subcategoryId);


    //    public
    BasicMessageResponse<List<CategoryBasicInfoProjection>> getCategories_public();

}
