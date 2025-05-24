package vn.graybee.taxonomy.category.service;

import vn.graybee.account.security.UserDetail;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.taxonomy.category.dto.request.CategoryCreateRequest;
import vn.graybee.taxonomy.category.dto.request.CategoryUpdateRequest;
import vn.graybee.taxonomy.category.dto.response.CategoryDto;
import vn.graybee.taxonomy.category.dto.response.CategoryIdManufacturerIdDto;
import vn.graybee.taxonomy.category.dto.response.CategoryIdSubcategoryIdDto;
import vn.graybee.taxonomy.category.dto.response.SidebarDto;
import vn.graybee.taxonomy.dto.response.UpdateStatusDto;

import java.util.List;

public interface CategoryService {

    BasicMessageResponse<CategoryDto> create(CategoryCreateRequest request);

    BasicMessageResponse<CategoryDto> update(int id, CategoryUpdateRequest request);

    BasicMessageResponse<CategoryDto> findById(int id);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<List<Integer>> deleteByIds(List<Integer> ids);

    BasicMessageResponse<List<CategoryDto>> fetchCategories_ADMIN();

    BasicMessageResponse<CategoryIdManufacturerIdDto> deleteRelationByCategoryIdAndManufacturerId(int categoryId, int manufacturerId);

    BasicMessageResponse<CategoryIdSubcategoryIdDto> deleteRelationBySubcategoryByCategoryId(int categoryId, int subcategoryId);

    BasicMessageResponse<UpdateStatusDto> updateStatusById(int id, String status);

    BasicMessageResponse<CategoryDto> restoreById(int id, UserDetail userDetail);

    BasicMessageResponse<List<SidebarDto>> getSidebar();

}
