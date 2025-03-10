package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.category.SubCategoryProjection;
import vn.graybee.projections.category.SubCategorySummaryProject;
import vn.graybee.requests.categories.SubCategoryCreateRequest;
import vn.graybee.requests.categories.SubCategoryUpdateRequest;
import vn.graybee.response.categories.SubCategoryResponse;

import java.util.List;

public interface SubCategoryServices {

    BasicMessageResponse<SubCategoryResponse> create(SubCategoryCreateRequest request);

    BasicMessageResponse<SubCategoryResponse> update(int subCategoryId, SubCategoryUpdateRequest request);

    BasicMessageResponse<List<SubCategorySummaryProject>> findByCategoryId(int categoryId);

    BasicMessageResponse<SubCategoryProjection> findById(int id);

    BasicMessageResponse<List<SubCategoryProjection>> fetchAll();

}
