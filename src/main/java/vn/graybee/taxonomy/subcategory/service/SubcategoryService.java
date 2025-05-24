package vn.graybee.taxonomy.subcategory.service;

import vn.graybee.account.security.UserDetail;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.taxonomy.dto.response.UpdateStatusDto;
import vn.graybee.taxonomy.subcategory.dto.request.SubcategoryCreateRequest;
import vn.graybee.taxonomy.subcategory.dto.request.SubcategoryUpdateRequest;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryIdTagIdDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryTagsDto;

import java.util.List;

public interface SubcategoryService {

    BasicMessageResponse<SubcategoryDto> create(SubcategoryCreateRequest request);

    BasicMessageResponse<SubcategoryDto> update(int id, SubcategoryUpdateRequest request);

    BasicMessageResponse<UpdateStatusDto> updateStatusById(int id, String status);

    BasicMessageResponse<SubcategoryDto> restoreById(int id, UserDetail userDetail);

    BasicMessageResponse<SubcategoryDto> getById(int id);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<SubcategoryIdTagIdDto> deleteRelationsBySubCategoryIdAndTagId(int subcategoryId, int tagId);

    BasicMessageResponse<List<SubcategoryDto>> fetchAll();

    List<SubcategoryTagsDto> getForSidebar();

}
