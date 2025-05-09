package vn.graybee.services.categories;

import vn.graybee.enums.DirectoryStatus;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.users.UserPrincipal;
import vn.graybee.requests.directories.CategoryCreateRequest;
import vn.graybee.requests.directories.CategoryUpdateRequest;
import vn.graybee.response.admin.directories.category.CategoryManufacturerIdResponse;
import vn.graybee.response.admin.directories.category.CategoryResponse;
import vn.graybee.response.admin.directories.category.CategorySubcategoryIdResponse;
import vn.graybee.response.admin.directories.general.UpdateStatusResponse;
import vn.graybee.response.publics.sidebar.SidebarDto;

import java.util.List;

public interface CategoryService {

    BasicMessageResponse<CategoryResponse> create(CategoryCreateRequest request);

    BasicMessageResponse<CategoryResponse> update(int id, CategoryUpdateRequest request);

    BasicMessageResponse<CategoryResponse> findById(int id);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<List<Integer>> deleteByIds(List<Integer> ids);

    BasicMessageResponse<List<CategoryResponse>> fetchCategories_ADMIN();

    BasicMessageResponse<CategoryManufacturerIdResponse> deleteRelationByCategoryIdAndManufacturerId(int categoryId, int manufacturerId);

    BasicMessageResponse<CategorySubcategoryIdResponse> deleteRelationBySubcategoryByCategoryId(int categoryId, int subcategoryId);

    BasicMessageResponse<UpdateStatusResponse> updateStatusById(int id, DirectoryStatus status);

    BasicMessageResponse<CategoryResponse> restoreById(int id, UserPrincipal userPrincipal);

    BasicMessageResponse<List<SidebarDto>> getSidebar();

}
