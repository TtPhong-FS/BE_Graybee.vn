package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.requests.directories.SubCategoryCreateRequest;
import vn.graybee.requests.directories.SubCategoryUpdateRequest;
import vn.graybee.response.admin.directories.general.UpdateStatusResponse;
import vn.graybee.response.admin.directories.subcate.SubCategoryResponse;
import vn.graybee.response.admin.directories.subcate.SubcategoryTagIdResponse;
import vn.graybee.response.publics.sidebar.SubcategoryDto;

import java.util.List;

public interface SubCategoryServices {

    BasicMessageResponse<SubCategoryResponse> create(SubCategoryCreateRequest request);

    BasicMessageResponse<SubCategoryResponse> update(int id, SubCategoryUpdateRequest request);

    BasicMessageResponse<UpdateStatusResponse> updateStatusById(int id, String status);

    BasicMessageResponse<SubCategoryResponse> getById(int id);

    BasicMessageResponse<Integer> delete(int id);


    BasicMessageResponse<SubcategoryTagIdResponse> deleteRelationsBySubCategoryIdAndTagId(int subcategoryId, int tagId);

    BasicMessageResponse<List<SubCategoryResponse>> fetchAll();

    List<SubcategoryDto> getForSidebar();

}
