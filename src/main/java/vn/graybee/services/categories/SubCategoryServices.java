package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.admin.category.SubCategoryProjection;
import vn.graybee.projections.admin.category.SubCategorySummaryProject;
import vn.graybee.requests.directories.SubCategoryCreateRequest;
import vn.graybee.requests.directories.SubCategoryUpdateRequest;
import vn.graybee.response.admin.directories.subcate.SubCategoryResponse;

import java.util.List;

public interface SubCategoryServices {

    BasicMessageResponse<SubCategoryResponse> create(SubCategoryCreateRequest request);

    BasicMessageResponse<SubCategoryResponse> update(int id, SubCategoryUpdateRequest request);

    BasicMessageResponse<SubCategoryResponse> getById(int id);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<List<SubCategorySummaryProject>> findByCategoryId(int categoryId);
    
    BasicMessageResponse<List<SubCategoryProjection>> fetchAll();

}
