package vn.graybee.modules.catalog.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.catalog.dto.request.CategoryRequest;
import vn.graybee.modules.catalog.dto.response.CategoryDto;
import vn.graybee.modules.catalog.dto.response.CategorySimpleDto;
import vn.graybee.modules.catalog.dto.response.CategorySummaryDto;
import vn.graybee.modules.catalog.dto.response.ChildrenIdAndParentId;
import vn.graybee.modules.catalog.dto.response.SidebarDto;
import vn.graybee.modules.catalog.dto.response.UpdateStatusDto;

import java.util.List;

public interface CategoryService {

    BasicMessageResponse<CategorySimpleDto> createCategory(CategoryRequest request);

    BasicMessageResponse<CategorySimpleDto> updateCategory(Long id, CategoryRequest request);

    BasicMessageResponse<CategoryDto> getCategoryById(Long id);

    BasicMessageResponse<Long> deleteById(Long id);

    BasicMessageResponse<List<Long>> deleteByIds(List<Long> ids);

    BasicMessageResponse<ChildrenIdAndParentId> removeChildrenByParentIdAndChildrenId(Long parentId, Long childrenId);

    BasicMessageResponse<List<CategoryDto>> getAllCategoryDtoForDashboard();

    BasicMessageResponse<UpdateStatusDto> updateStatusById(Long id, String status);

    BasicMessageResponse<CategoryDto> restoreById(Long id, UserDetail userDetail);

    void updateProductCount(Long id, boolean isIncrease);

    void checkExistsById(Long id);

    CategorySummaryDto getCategorySummaryByName(String name);

    BasicMessageResponse<List<SidebarDto>> getSidebar();

}
